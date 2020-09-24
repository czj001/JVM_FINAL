package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LLOAD extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        long val = frame.getLocalVars().getLong(this.index);
        frame.getOperandStack().pushLong(val);
    }
}
