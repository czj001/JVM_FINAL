package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LSTORE_N extends NoOperandsInstruction {
    private int index;

    public LSTORE_N(int const_val){
        assert const_val>=0 && const_val <=3;
        this.index = const_val;
    }

    @Override
    public void execute(StackFrame frame) {
        long val = frame.getOperandStack().popLong();
        frame.getLocalVars().setLong(this.index, val);
    }
}
