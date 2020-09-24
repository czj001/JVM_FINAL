package com.njuse.jvmfinal.instruction.math.operation;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LADD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        long val2 = stack.popLong();
        long val1 = stack.popLong();
        long res = val1 + val2;
        stack.pushLong(res);
    }
}
