package com.njuse.jvmfinal.instruction.control;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

public class RETURN extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        JThread thread = frame.getThread();
        thread.popFrame();
    }
}
