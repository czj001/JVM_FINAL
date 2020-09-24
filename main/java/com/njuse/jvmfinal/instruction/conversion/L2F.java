package com.njuse.jvmfinal.instruction.conversion;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class L2F extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        long val1 = stack.popLong();
        float val2 = (float)val1;
        stack.pushFloat(val2);
    }
}
