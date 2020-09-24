package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FSTORE_N extends NoOperandsInstruction {
    private int index;

    public FSTORE_N(int const_val){
        assert const_val>=0 && const_val <=3;
        this.index = const_val;
    }

    @Override
    public void execute(StackFrame frame) {
        float value = frame.getOperandStack().popFloat();
        frame.getLocalVars().setFloat(this.index, value);
    }
}
