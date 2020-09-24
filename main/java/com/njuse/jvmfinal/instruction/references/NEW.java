package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.NonArrayObject;

public class NEW extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        ClassRef classRef = (ClassRef) frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(super.index);
        try {
            JClass clazz = classRef.resolveClassRef();

            if (clazz.getInitState() == InitState.PREPARED) {
                frame.setNextPC(frame.getNextPC() - 3);// opcode + operands = 3
                clazz.initClass(frame.getThread(), clazz);
                return;
            }

            if (clazz.isInterface() || clazz.isAbstract()) {
                throw new InstantiationError();
            }
            NonArrayObject ref = clazz.newObject();
            JHeap.getInstance().addObject(ref);
            frame.getOperandStack().pushObjectRef(ref);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
    }
    }
}
