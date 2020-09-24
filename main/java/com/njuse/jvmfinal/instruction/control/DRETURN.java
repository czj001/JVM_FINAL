package com.njuse.jvmfinal.instruction.control;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DRETURN extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        double value = frame.getOperandStack().popDouble();
        frame.getThread().popFrame();
        frame.getThread().getCurrentFrame().getOperandStack().pushDouble(value);
    }
}
