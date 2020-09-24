package com.njuse.jvmfinal.instruction.conversion;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class I2B extends NoOperandsInstruction {

    @Override
    public void execute(StackFrame frame) {
        int val = frame.getOperandStack().popInt();
        byte b = (byte) val;
        frame.getOperandStack().pushInt( b );
    }
}
