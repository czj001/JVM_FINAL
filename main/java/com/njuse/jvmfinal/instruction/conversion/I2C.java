package com.njuse.jvmfinal.instruction.conversion;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class I2C extends NoOperandsInstruction {

    @Override
    public void execute(StackFrame frame) {
        int val = frame.getOperandStack().popInt();
        char ch = (char)val;
        int res = ((int)ch) & 0x0ffff;
        frame.getOperandStack().pushInt(res);
    }
}