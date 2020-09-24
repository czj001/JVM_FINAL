package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class ISTORE extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
            int val = frame.getOperandStack().popInt();
            frame.getLocalVars().setInt(this.index,val);
    }
}
