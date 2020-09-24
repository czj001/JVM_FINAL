package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class ASTORE extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        JObject ref = frame.getOperandStack().popObjectRef();
        frame.getLocalVars().setObjectRef(this.index, ref);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
