package com.njuse.jvmfinal.instruction.conversion;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class I2D extends NoOperandsInstruction {

    @Override
    public void execute(StackFrame frame) {
        int val = frame.getOperandStack().popInt();
        frame.getOperandStack().pushDouble((double) val );
    }
}