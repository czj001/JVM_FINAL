package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.NullObject;

public class ACONST_NULL extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushObjectRef(new NullObject());
    }
}
