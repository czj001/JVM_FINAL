package com.njuse.jvmfinal.instruction.control;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class ARETURN extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        JObject ref = frame.getOperandStack().popObjectRef();
        frame.getThread().popFrame();
        frame.getThread().getCurrentFrame().getOperandStack().pushObjectRef(ref);
    }
}
