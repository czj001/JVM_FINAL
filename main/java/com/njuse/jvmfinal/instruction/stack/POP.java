package com.njuse.jvmfinal.instruction.stack;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class POP extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        frame.getOperandStack().popSlot();
    }
}
