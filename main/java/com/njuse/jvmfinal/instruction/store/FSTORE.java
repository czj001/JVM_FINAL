package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FSTORE extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        float value = frame.getOperandStack().popFloat();
        frame.getLocalVars().setFloat(this.index, value);
    }
}
