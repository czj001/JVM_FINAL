package com.njuse.jvmfinal.instruction.control;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LRETURN extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        long value = frame.getOperandStack().popLong();
        JThread thread = frame.getThread();
        thread.popFrame();
        thread.getCurrentFrame().getOperandStack().pushLong(value);
    }
}
