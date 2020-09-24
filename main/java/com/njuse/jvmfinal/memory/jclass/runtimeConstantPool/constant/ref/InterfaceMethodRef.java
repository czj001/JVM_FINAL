package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.InterfaceMethodrefInfo;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

@Getter
@Setter
public class InterfaceMethodRef extends MemberRef {
    private Method method;

    public InterfaceMethodRef(RuntimeConstantPool runtimeConstantPool, InterfaceMethodrefInfo interfaceMethodrefInfo) {
        super(runtimeConstantPool, interfaceMethodrefInfo);
        //method
    }
    public Method resolveInterfaceMethodRef(JClass clazz) {
        // no need to resolve class
        assert clazz != null ;

        Optional<Method> optionalMethod;
        for (JClass currentClazz = clazz; currentClazz != null; currentClazz = currentClazz.getSuperClass()) {
            optionalMethod = currentClazz.findMethod(name, descriptor);
            if (optionalMethod.isPresent()) {
                method = optionalMethod.get();
                return method;
            }
        }

        //Not found in superclass
        //find in superinterfaces
        JClass[] ifs = clazz.getInterfaces();
        Stack<JClass> interfaces = new Stack<>();
        interfaces.addAll(Arrays.asList(ifs));
        while (!interfaces.isEmpty()) {
            JClass clz = interfaces.pop();
            optionalMethod = clz.findMethod(this.name, this.descriptor);
            if (optionalMethod.isPresent()) {
                method = optionalMethod.get();
                return method;
            }

            interfaces.addAll(Arrays.asList(clz.getInterfaces()));
        }

        return method;
    }
    public Method resolveInterfaceMethodRef() {
        if(method == null) {
            try {
                clazz = resolveClassRef();
                return resolveInterfaceMethodRef(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return method;
    }


    public String toString() {
        return "InterfaceMethodRef to " + className;
    }

}
