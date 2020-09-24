package com.njuse.jvmfinal.instruction.comparison;

import com.njuse.jvmfinal.instruction.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public abstract class IF_ICMPCOND extends BranchInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack os = frame.getOperandStack();
        int val2 = os.popInt();
        int val1 = os.popInt();
        if(condition(val1,val2)){
            int branchID = frame.getNextPC() - 3 + offset;
            frame.setNextPC(branchID);
        }

    }

    protected abstract boolean condition(int v1, int v2);
}
