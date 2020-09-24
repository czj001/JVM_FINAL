package com.njuse.jvmfinal.memory;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MethodArea {
    private static Map<String, JClass> classMap;
    private static MethodArea methodArea = new MethodArea();


    private MethodArea(){
        classMap = new LinkedHashMap<>();
    }


    public static JClass findClass(String className){
        if(classMap.containsKey(className)){
            return classMap.get(className);
        }else {
            return null;
        }
    }
    public static void addClass(JClass clazz){
        classMap.put(clazz.getName(),clazz);
    }
    public static MethodArea getInstance(){
        return methodArea;
    }


}
