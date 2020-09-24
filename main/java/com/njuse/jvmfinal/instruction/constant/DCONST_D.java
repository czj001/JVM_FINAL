package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DCONST_D extends NoOperandsInstruction {
    private double val;

    public DCONST_D(double cons_val){
        assert cons_val == 1.0D || cons_val == 0.0D;
        this.val = cons_val;
    }

    @Override
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushDouble(this.val);
    }
}
