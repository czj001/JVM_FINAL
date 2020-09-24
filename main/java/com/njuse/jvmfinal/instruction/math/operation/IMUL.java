package com.njuse.jvmfinal.instruction.math.operation;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class IMUL extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack os = frame.getOperandStack();
        int val2 = os.popInt();
        int val1 = os.popInt();
        int res = val1 * val2;
        os.pushInt(res);
    }
}
