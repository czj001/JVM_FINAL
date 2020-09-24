package com.njuse.jvmfinal.instruction.math.operation;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class ISUB extends NoOperandsInstruction {

    @Override
    public void execute(StackFrame frame) {
        OperandStack os = frame.getOperandStack();
        int val2 = os.popInt();
        int val1 = os.popInt();
        os.pushInt(val1 - val2);
    }
}
