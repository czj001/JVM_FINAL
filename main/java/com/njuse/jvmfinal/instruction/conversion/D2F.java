package com.njuse.jvmfinal.instruction.conversion;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class D2F extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        double val1 = stack.popDouble();
        float val2 = (float)val1;
        stack.pushFloat(val2);
    }
}
