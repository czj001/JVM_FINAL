package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.FloatWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.IntWrapper;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LDC extends Index8Instruction {

    @Override
    public void execute(StackFrame frame) {
        //当前操作数栈
        OperandStack stack = frame.getOperandStack();
        //运行时常量池中对应的元素
        Constant constant = frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(this.index);
        if (constant instanceof IntWrapper) {
            stack.pushInt(((IntWrapper) constant).getValue());
        }
        else if (constant instanceof FloatWrapper) {
            stack.pushFloat(((FloatWrapper)constant).getValue());
        }

        else throw new ClassFormatError();
    }
}
