package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.classloader.ClassLoader;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SymRef implements Constant {
    public RuntimeConstantPool runtimeConstantPool;
    public String className;    //format : java/lang/Object
    public JClass clazz;

    public JClass resolveClassRef() throws ClassNotFoundException {
        if(clazz == null) {
            JClass D = runtimeConstantPool.getClazz();
            JClass C = ClassLoader.getInstance().loadClass(className, D.getLoadEntryType());
            if (!C.isAccessibleTo(D)) {
                throw new IllegalAccessError();
            }
            clazz = C;
        }
        return clazz;
    }

}
