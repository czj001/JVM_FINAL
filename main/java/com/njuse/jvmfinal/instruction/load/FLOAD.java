package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FLOAD extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        float val = frame.getLocalVars().getFloat(this.index);
        frame.getOperandStack().pushFloat(val);
    }
}
