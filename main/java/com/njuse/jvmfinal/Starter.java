package com.njuse.jvmfinal;


import com.njuse.jvmfinal.classloader.ClassLoader;
//import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfilereader.ClassFileReader;
import com.njuse.jvmfinal.execution.Interpreter;
//import com.njuse.jvmfinal.memory.MethodArea;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.io.File;
//import java.io.IOException;

public class Starter {
    public static void main(String[] args) {
        //用于测试2333333
        String cp = String.join(File.separator, "src", "test", "java");
        runTest("cases.light.LightEasyStaticTest", cp);

    }

    /**
     * ⚠️警告：不要改动这个方法签名，这是和测试用例的唯一接口
     */
    public static void runTest(String mainClassName, String cp) {
        mainClassName = mainClassName.replace(".","/");
        ClassFileReader.setUserClasspath(cp);
        ClassLoader cl = ClassLoader.getInstance();
        try{
            JClass clazz = cl.loadClass(mainClassName,null);
            JThread thread = new JThread();
            Method main = clazz.getMainMethod();
            StackFrame stackFrame = new StackFrame(thread, main, main.getMaxStack(), main.getMaxLocal());
            thread.pushFrame(stackFrame);
            if(clazz.getClinitMethod() != null){
                Method clinit = clazz.getClinitMethod();
                assert clinit != null;
                StackFrame newFrame = new StackFrame(thread, clinit, clinit.getMaxStack(), clinit.getMaxLocal() + 1);
                thread.pushFrame(newFrame);
                clazz.setInitState(InitState.SUCCESS);
            }
            Interpreter.interpret(thread);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }


    }


}
