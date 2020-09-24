package com.njuse.jvmfinal.instruction.comparison;

import com.njuse.jvmfinal.instruction.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public abstract class IFCOND extends BranchInstruction {
    @Override
    public void execute(StackFrame frame) {
        int val = frame.getOperandStack().popInt();
        if(condition(val)){
            int branchID = frame.getNextPC() - 3 + offset;
            frame.setNextPC(branchID);
        }
    }
    protected abstract boolean condition(int value);
}
