package com.njuse.jvmfinal.instruction.comparison;

import com.njuse.jvmfinal.instruction.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class IFNULL extends BranchInstruction {
    @Override
    public void execute(StackFrame frame) {
        JObject ref = frame.getOperandStack().popObjectRef();
        if (ref.isNull()) {
            int branchPC = frame.getNextPC() - 3 + super.offset;
            frame.setNextPC(branchPC);
        }
    }
}
