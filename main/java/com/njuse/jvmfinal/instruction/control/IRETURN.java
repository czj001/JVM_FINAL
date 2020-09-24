package com.njuse.jvmfinal.instruction.control;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

public class IRETURN extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        int var= frame.getOperandStack().popInt();
        JThread thread = frame.getThread();
        thread.popFrame();
        thread.getCurrentFrame().getOperandStack().pushInt(var);
    }
}
