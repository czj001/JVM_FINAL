package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class INSTANCEOF extends Index16Instruction {

    @Override
    public void execute(StackFrame frame) {
        JClass currentClazz = frame.getMethod().getClazz();
        RuntimeConstantPool constantPool = currentClazz.getRuntimeConstantPool();
        Constant classRef = constantPool.getConstant(this.index);
        assert classRef instanceof ClassRef;
        JObject ref = frame.getOperandStack().popObjectRef();
        if(ref.isNull()){
            frame.getOperandStack().pushInt(0);
        }else {
            try {
                JClass clazz = ((ClassRef) classRef).resolveClassRef();
                if(ref.isInstanceOf(clazz)){
                    frame.getOperandStack().pushInt(1);
                }else {
                    frame.getOperandStack().pushInt(0);
                }
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

        }

    }
}
