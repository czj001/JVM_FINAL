package com.njuse.jvmfinal.memory.jclass;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.classloader.classfilereader.EntryType;
import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfileparser.FieldInfo;
import com.njuse.jvmfinal.classloader.classfileparser.MethodInfo;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.MethodRef;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import com.njuse.jvmfinal.runtime.data.JObject.NonArrayObject;
import com.njuse.jvmfinal.runtime.data.JObject.array.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Optional;

@Getter
@Setter
public class JClass {
    private short accessFlags;
    private String name;
    private String superClassName;
    private String[] interfaceNames;
    private RuntimeConstantPool runtimeConstantPool;
    private Field[] fields;
    private Method[] methods;
    private EntryType loadEntryType;
    private JClass superClass;
    private JClass[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private Vars staticVars;
    private InitState initState;

    public JClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags();
        this.name = classFile.getClassName();
        if (!this.name.equals("java/lang/Object")) {
            // index of super class of java/lang/Object is 0
            this.superClassName = classFile.getSuperClassName();
        } else {
            this.superClassName = "";
        }
        this.interfaceNames = classFile.getInterfaceNames();
        this.fields = parseFields(classFile.getFields());
        this.methods = parseMethods(classFile.getMethods());
        this.runtimeConstantPool = parseRuntimeConstantPool(classFile.getConstantPool());
    }
    public JClass(){
        //创建数组类
    }

    private Field[] parseFields(FieldInfo[] info) {
        int len = info.length;
        fields = new Field[len];
        for (int i = 0; i < len; i++) {
            fields[i] = new Field(info[i], this);
        }
        return fields;
    }

    private Method[] parseMethods(MethodInfo[] info) {
        int len = info.length;
        methods = new Method[len];
        for (int i = 0; i < len; i++) {
            methods[i] = new Method(info[i], this);
        }
        return methods;
    }

    private RuntimeConstantPool parseRuntimeConstantPool(ConstantPool cp) {
        return new RuntimeConstantPool(cp, this);
    }



    public Optional<Method> findMethod(String name, String descriptor) {
        for (Method m : methods) {
            if (m.getDescriptor().equals(descriptor) && m.getName().equals(name)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }


    /**
     * initialize class
     * @param thread
     * @param clazz
     */
    public void initClass(JThread thread, JClass clazz){
        initStart(clazz);

        preClinit(thread,clazz);

        initSuperClass(thread,clazz);

        initSucceed(clazz);
    }

    private void preClinit(JThread thread,JClass clazz){
        Method clinit = clazz.getClinitMethod();
        if(clinit != null){
            StackFrame frame = new StackFrame(thread,clinit,clinit.getMaxStack(),clinit.getMaxLocal()+1);
            thread.pushFrame(frame);
        }
    }
    private void initSuperClass(JThread thread, JClass clazz){
        if(!clazz.isInterface()){
            JClass superClazz = clazz.getSuperClass();
            if(superClazz!=null && superClazz.getInitState() == InitState.PREPARED){
                initClass(thread,superClazz);
            }
        }
    }

    /**
     * find clinit & main
     * @return
     */
    public Method getMainMethod() {
        return getMethodInClass("main", "([Ljava/lang/String;)V", true);
    }

    public Method getClinitMethod(){
        return getMethodInClass("<clinit>","()V",true);
    }

    private Method getMethodInClass(String name, String descriptor, boolean isStatic) {
        JClass clazz = this;
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.getDescriptor().equals(descriptor)
                    && m.getName().equals(name)
                    && m.isStatic() == isStatic) {
                return m;
            }
        }
        return null;
    }

    /**
     * set initialing status
     * @param clazz
     */

    private void initStart(JClass clazz) {
        clazz.initState = InitState.BUSY;
    }
    private void initSucceed(JClass clazz) {
        clazz.initState = InitState.SUCCESS;
    }
    private void initFail() {
        this.initState = InitState.FAIL;
    }




    public NonArrayObject newObject() {
        return new NonArrayObject(this);
    }

    public ArrayObject newArrayObject(int len){
        if (this.name.charAt(0) != '[') {
            throw new RuntimeException("This Class is not array: " + this.name);
        }else {
            switch (this.name){
                case "[I":
                    return new IntArrayObject(len, this.name, this);
                case "[Z":
                    return new BooleanArrayObject(len, this.name,this);
                case "[B":
                    return new ByteArrayObject(len, this.name, this);
                case "[C":
                    return new CharArrayObject(len, this.name, this);
                case "[S":
                    return new ShortArrayObject(len, this.name, this);
                case "[F":
                    return new FloatArrayObject(len, this.name, this);
                case "[J":
                    return new LongArrayObject(len, this.name, this);
                case "[D":
                    return new DoubleArrayObject(len, this.name, this);
                default:
                    return new RefArrayObject(len, this.name, this);

            }
        }
    }

    public JClass getComponentRefClass() {
        if (this.name.charAt(0) != '[') throw new RuntimeException("Invalid Array:" + this.name);
        ClassLoader loader = ClassLoader.getInstance();
        String componentTypeDescriptor = this.name.substring(1);
        String classToFormArray = null;
        if (componentTypeDescriptor.charAt(0) == '[') {
            classToFormArray = componentTypeDescriptor;
        } else if (componentTypeDescriptor.charAt(0) == 'L') {
            //remove first and last char 'L' and ';'
            classToFormArray = componentTypeDescriptor.substring(1, componentTypeDescriptor.length() - 1);
        }
        try{
            return loader.loadClass(classToFormArray, this.loadEntryType);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Cannot load arrayClass:" + classToFormArray);
        }
    }
    public String getArrayPrimitiveType(){
        if (this.name.charAt(0) != '[') throw new RuntimeException("Invalid Array:" + this.name);
        String type = this.name.substring(1);
        if(type.equals("Z") || type.equals("B") || type.equals("S")||
             type.equals("C")|| type.equals("I")|| type.equals("J")||
               type.equals("F")|| type.equals("D")){
            return type;
        }else
            return null;

    }

    /**
     * get more information
     * @return
     */
    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }
    public boolean isAbstract() {
        return (this.accessFlags & AccessFlags.ACC_ABSTRACT) != 0;
    }
    public boolean isAccSuper(){
        return 0 != (this.accessFlags & AccessFlags.ACC_SUPER);
    }
    public boolean isInterface(){
        return (accessFlags & AccessFlags.ACC_INTERFACE) != 0;
    }
    public boolean isArray() {
        return this.name.charAt(0) == '[';
    }
    public boolean isObject() {
        return this.name.equals("java/lang/Object");
    }

    public boolean isCloneable() {
        return this.name.equals("java/lang/Cloneable");
    }

    public boolean isSerializable() {
        return this.name.equals("java/io/Serializable");
    }

    public String getPackageName(){
        int index = name.lastIndexOf("/");
        if(index > 0){
            return name.substring(0,index);
        }else
            return "";
    }
    public boolean isAccessibleTo(JClass caller){
        boolean isPublic = isPublic();
        boolean isTheSamePackage = caller.getPackageName().equals(this.getPackageName());
        return isPublic||isTheSamePackage;
    }

    // todo
    /**
     * check after pushed
     * @param T
     * @return
     */
    public boolean determineInstance(JClass T){
        JClass S = this;
        if(S == T){
            return true;
        }else if(!S.isArray()){
            if(!S.isInterface()){
                if(!T.isInterface()){
                    return S.isSubClassOf(T);
                }else {
                    return S.isImplementOf(T);
                }
            }else {
                if(!T.isInterface()){
                    return T.isObject();
                }else {
                    return T.isSuperInterfaceOf(S);
                }
            }
        }else {
            if(!T.isArray()){
                if(!T.isInterface()){
                    return T.isObject();
                }else {
                    return T.isCloneable() || T.isSerializable();
                }
            }else {
                if(S.getArrayPrimitiveType() != null && T.getArrayPrimitiveType()!=null
                        && S.getArrayPrimitiveType().equals(T.getArrayPrimitiveType())){
                    return true;
                }else {
                    JClass SC = S.getComponentRefClass();
                    JClass TC = T.getComponentRefClass();
                    return SC ==TC || T.isSerializable();
                }
            }

        }

    }
    public boolean isSubClassOf(JClass C){
        JClass clazz = this;
        while(clazz.getSuperClass() != null){
            if(clazz.getSuperClass() == C){
                return true;
            }
            clazz = clazz.getSuperClass();
        }
        return false;
    }
    public boolean isSubInterfaceOf(JClass otherInterface) {
        JClass[] superInterfaces = this.getInterfaces();
        for (JClass i : superInterfaces) {
            if (i == otherInterface || i.isSubInterfaceOf(otherInterface))
                return true;
        }
        return false;
    }
    private boolean isImplementOf(JClass otherInterface) {
        JClass superClass = this;
        while (superClass != null) {
            for (JClass i : superClass.getInterfaces()) {
                if (i == otherInterface || i.isSubInterfaceOf(otherInterface))
                    return true;
            }
            superClass = this.getSuperClass();
        }
        return false;
    }
    private boolean isSuperInterfaceOf(JClass otherInterface) {
        return otherInterface.isSubInterfaceOf(this);
    }


}
