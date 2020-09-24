package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class ALOAD extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        JObject ref = frame.getLocalVars().getObjectRef(this.index);
        frame.getOperandStack().pushObjectRef(ref);
    }
}
