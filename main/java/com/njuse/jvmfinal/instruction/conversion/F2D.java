package com.njuse.jvmfinal.instruction.conversion;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class F2D extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        float val1 = stack.popFloat();
        double val2 = (double)val1;
        stack.pushDouble(val2);
    }
}
