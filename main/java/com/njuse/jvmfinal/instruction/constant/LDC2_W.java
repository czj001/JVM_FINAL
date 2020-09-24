package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.DoubleWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.LongWrapper;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LDC2_W extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        Constant constant = frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(this.index);
        if (constant instanceof LongWrapper) {
            stack.pushLong(((LongWrapper)constant).getValue());
        } else if(constant instanceof  DoubleWrapper){
            stack.pushDouble(((DoubleWrapper)constant).getValue());
        }
    }
}
