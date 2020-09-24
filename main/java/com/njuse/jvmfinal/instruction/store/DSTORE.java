package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DSTORE extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        double value = frame.getOperandStack().popDouble();
        frame.getLocalVars().setDouble(this.index, value);
    }
}
