package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LCONST_L extends NoOperandsInstruction {

    private long val;

    public LCONST_L(long const_val){
        assert const_val>=0l && const_val <= 1l;
        this.val = const_val;
    }
    @Override
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushLong(this.val);
    }
}
