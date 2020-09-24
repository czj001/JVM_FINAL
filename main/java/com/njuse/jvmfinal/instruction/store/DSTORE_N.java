package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DSTORE_N extends NoOperandsInstruction {
    private int index;

    public DSTORE_N(int cons_val){
        assert  cons_val >=0 && cons_val <=3;
        this.index = cons_val;
    }


    @Override
    public void execute(StackFrame frame) {
        double value = frame.getOperandStack().popDouble();
        frame.getLocalVars().setDouble(this.index, value);

    }
}
