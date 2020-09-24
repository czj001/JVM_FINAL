package com.njuse.jvmfinal.classloader;

import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfilereader.ClassFileReader;
import com.njuse.jvmfinal.classloader.classfilereader.EntryType;
import com.njuse.jvmfinal.memory.MethodArea;
import com.njuse.jvmfinal.memory.jclass.AccessFlags;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.runtime.data.JObject.NullObject;
import org.apache.commons.lang3.tuple.Pair;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.*;
import java.io.IOException;


public class ClassLoader {
    private static ClassLoader classLoader = new ClassLoader();
    private  ClassFileReader classFileReader;
    private MethodArea methodArea;

    private ClassLoader(){
        classFileReader = ClassFileReader.getInstance();
        methodArea = MethodArea.getInstance();
        ClassFileReader.initBootAndExt();
    }


    public  JClass loadClass(String className, EntryType initialingType) throws ClassNotFoundException{
        if(methodArea.findClass(className)== null){
            return className.charAt(0) == '['  ?  this.loadArrayClass(className, initialingType) : this.loadNonArrayClass(className, initialingType);
        } else {
            return methodArea.findClass(className);
        }
    }
    private JClass loadNonArrayClass(String className, EntryType initialingType) throws ClassNotFoundException{
        try {
            Pair<byte[], Integer> clazzbytes = classFileReader.findClass(className, initialingType);
            byte[] data = clazzbytes.getKey();
            EntryType definingType = new EntryType(clazzbytes.getValue());
            JClass clazz = defineClass(data, definingType);
            linkClass(clazz);
            return clazz;

        }catch (IOException e){
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    // for array class
    private JClass loadArrayClass(String className, EntryType initialingType) throws ClassNotFoundException {
        //自行构建JClass， 缓存在方法区中
        JClass ret = new JClass();
        ret.setAccessFlags((short) AccessFlags.ACC_PUBLIC);
        ret.setName(className);
        ret.setInitState(InitState.SUCCESS);
        this.methodArea.addClass(ret);

        // 设置父类，父接口
        ret.setSuperClass(this.loadClass("java/lang/Object", initialingType));
        JClass[] interfaces = new JClass[]{this.loadClass("java/lang/Cloneable", initialingType),this.loadClass("java/io/Serializable", initialingType)};
        ret.setInterfaces(interfaces);


        return ret;
    }


    private JClass defineClass(byte[] data, EntryType initialingType) throws ClassNotFoundException
    {
        //creat JClass from binary representation
        ClassFile classFile = new ClassFile(data);
        JClass jClass = new JClass(classFile);

        jClass.setLoadEntryType(initialingType);
        // recursively load superclass
        resolveSuperClass(jClass);

        // recursively load superinterfaces
        resolveSuperInterfaces(jClass);

        methodArea.addClass(jClass);

        return jClass;

    }
    private void resolveSuperClass(JClass clazz) throws ClassNotFoundException{
        if(!clazz.getName() .equals("java/lang/Object")){
            String superClassName = clazz.getSuperClassName();
            EntryType initialingType = clazz.getLoadEntryType();
            clazz.setSuperClass(loadClass(superClassName,initialingType));
        }

    }
    private void resolveSuperInterfaces(JClass clazz) throws ClassNotFoundException{
        String[] s = clazz.getInterfaceNames();
        JClass[] clazzes = new JClass[s.length];
        for(int i = 0; i < s.length; i++){
            clazzes[i] = loadClass(s[i],clazz.getLoadEntryType());
        }
        clazz.setInterfaces(clazzes);

    }


    private void linkClass(JClass clazz){
        verify(clazz);
        prepare(clazz);

    }
    private void verify(JClass clazz){

    }
    private void prepare(JClass clazz){
        calInstanceFieldSlotIDs(clazz);
        calStaticFieldSlotIDs(clazz);
        allocAndInitStaticVars(clazz);
        clazz.setInitState(InitState.PREPARED);
    }



    private void calInstanceFieldSlotIDs(JClass clazz) {
        int slotID = 0;
        if (clazz.getSuperClass() != null) {
            slotID = clazz.getSuperClass().getInstanceSlotCount();
        }
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            if (!f.isStatic()) {
                f.setSlotID(slotID);
                slotID++;
                if (f.isLongOrDouble()) slotID++;
            }
        }
        clazz.setInstanceSlotCount(slotID);
    }
    private void calStaticFieldSlotIDs(JClass clazz) {
        int slotID = 0;
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            if (f.isStatic()) {
                f.setSlotID(slotID);
                slotID++;
                if (f.isLongOrDouble()) slotID++;
            }
        }
        clazz.setStaticSlotCount(slotID);

    }

    private void allocAndInitStaticVars(JClass clazz) {
        clazz.setStaticVars(new Vars(clazz.getStaticSlotCount()));
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            if(f.isFinal()&&f.isStatic()){
                loadValueFromRTCP(clazz,f);
            }else if(f.isStatic()){
                initDefaultValue(clazz,f);
            }

        }
    }
    private void initDefaultValue(JClass clazz, Field field) {
        Vars staticVars = clazz.getStaticVars();
        int slotID = field.getSlotID();
        String descriptor = field.getDescriptor();
        switch (descriptor.charAt(0)){
            case 'I':
            case 'S':
            case 'Z':
            case 'C':
            case 'B':
                staticVars.setInt(slotID,0);
                break;
            case 'F':
                staticVars.setFloat(slotID,0);
                break;
            case 'J':
                staticVars.setLong(slotID,0);
                break;
            case 'D':
                staticVars.setDouble(slotID,0);
                break;
            default:
                staticVars.setObjectRef(slotID,new NullObject());
                break;

        }
    }
    private void loadValueFromRTCP(JClass clazz, Field field) {
        Vars staticVars = clazz.getStaticVars();
        RuntimeConstantPool runtimeConstantPool = clazz.getRuntimeConstantPool();
        int slotID = field.getSlotID();
        int constantValueIndex = field.getConstValueIndex();
        String descriptor = field.getDescriptor();
        if(constantValueIndex >0 ) {
            switch (descriptor.charAt(0)) {
                case 'I':
                case 'S':
                case 'Z':
                case 'C':
                case 'B':
                    int intVar = ((IntWrapper) runtimeConstantPool.getConstant(constantValueIndex)).getValue();
                    staticVars.setInt(slotID, intVar);
                    break;
                case 'F':
                    float floatVar = ((FloatWrapper) runtimeConstantPool.getConstant(constantValueIndex)).getValue();
                    staticVars.setFloat(slotID, floatVar);
                    break;
                case 'J':
                    long longVar = ((LongWrapper) runtimeConstantPool.getConstant(constantValueIndex)).getValue();
                    staticVars.setLong(slotID, longVar);
                    break;
                case 'D':
                    double doubleVar = ((DoubleWrapper) runtimeConstantPool.getConstant(constantValueIndex)).getValue();
                    staticVars.setDouble(slotID, doubleVar);
                    break;
                default:
                    break;
            }
        }
    }

    public static ClassLoader getInstance(){return classLoader;}

}
