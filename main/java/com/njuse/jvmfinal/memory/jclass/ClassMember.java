package com.njuse.jvmfinal.memory.jclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ClassMember {
    public short accessFlags;
    public String name;
    public String descriptor;
    public JClass clazz;

    public boolean isStatic() {
        return (accessFlags & AccessFlags.ACC_STATIC) != 0;
    }
    public boolean isLongOrDouble() {
        return descriptor.equals("J") || descriptor.equals("D");
    }
    public boolean isFinal(){
        return (accessFlags & AccessFlags.ACC_FINAL) != 0;
    }
    public boolean isNative() {
        return 0 != (this.accessFlags & 256);
    }
    public boolean isPublic(){
        return (accessFlags & AccessFlags.ACC_PUBLIC) != 0;
    }
    public boolean isPrivate(){
        return (accessFlags & AccessFlags.ACC_PRIVATE) != 0;
    }
    public boolean isProtected(){
        return (accessFlags & AccessFlags.ACC_PROTECTED) != 0;
    }
    public boolean isAccessibleTo(JClass D){ // access check of field or method
        if (this.isPublic()) {
            return true;
        } else if(this.isProtected()){
            return this.clazz == D || D.isSubClassOf(this.clazz)|| this.clazz.getPackageName().equals(D.getPackageName());
        }else if(this.isPrivate()){
            return this.clazz == D;
        }else if(this.clazz.getPackageName().equals(D.getPackageName())){
            return true;
        }
        return false;
    }


}
