package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.FieldrefInfo;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldRef extends MemberRef {
    private Field field;

    public FieldRef(RuntimeConstantPool runtimeConstantPool, FieldrefInfo fieldrefInfo) {
        super(runtimeConstantPool, fieldrefInfo);
    }
    public Field resolveFieldRef() {
        if(field == null) {
            try {
                clazz = resolveClassRef();
                field = lookUpField(name, descriptor, clazz);
                if (field == null) {
                    throw new NoSuchFieldError();
                }
                if(!field.isAccessibleTo(this.clazz)){
                    throw new IllegalAccessError();
                }
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return field;
    }


    private Field lookUpField(String name, String descriptor, JClass clazz){
        for(Field f : clazz.getFields()){
            if(f.getDescriptor().equals(descriptor) && f.getName().equals(name)){
                return f;
            }
        }
        for(JClass interfaces:clazz.getInterfaces()){
            Field f = lookUpField(name, descriptor,  interfaces);
            if(f != null) return f;
        }
        if(clazz.getSuperClass() != null){
            Field f = lookUpField(name, descriptor, clazz.getSuperClass());
            if(f != null) return f;
        }
        return null;
    }
    @Override
    public String toString() {
        return "FieldRef to " + className;
    }
}
