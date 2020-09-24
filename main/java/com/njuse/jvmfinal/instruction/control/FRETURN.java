package com.njuse.jvmfinal.instruction.control;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FRETURN extends NoOperandsInstruction {

    @Override
    public void execute(StackFrame frame) {
        float value = frame.getOperandStack().popFloat();
        frame.getThread().popFrame();
        frame.getThread().getCurrentFrame().getOperandStack().pushFloat(value);
    }
}
