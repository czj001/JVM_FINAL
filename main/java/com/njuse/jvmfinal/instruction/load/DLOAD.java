package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DLOAD extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
            double val = frame.getLocalVars().getDouble(this.index);
            frame.getOperandStack().pushDouble(val);
    }
}
