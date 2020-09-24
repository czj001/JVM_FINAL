package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LSTORE extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        long val = frame.getOperandStack().popLong();
        frame.getLocalVars().setLong(this.index, val);
    }
}
