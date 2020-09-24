package com.njuse.jvmfinal.instruction.stack;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.Slot;

public class DUP extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        Slot slot = frame.getOperandStack().popSlot();
        frame.getOperandStack().pushSlot(slot.clone());
        frame.getOperandStack().pushSlot(slot.clone());
    }
}
