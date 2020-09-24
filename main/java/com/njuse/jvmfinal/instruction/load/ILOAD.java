package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class ILOAD extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        int val = frame.getLocalVars().getInt(this.index);
        frame.getOperandStack().pushInt(val);
    }
}
