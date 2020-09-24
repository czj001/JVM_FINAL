package com.njuse.jvmfinal.instruction.math.operation;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DADD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        double val2 = stack.popDouble();
        double val1 = stack.popDouble();
        double res = val1 + val2;
        stack.pushDouble(res);
    }
}
