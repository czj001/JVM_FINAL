package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FLOAD_N extends NoOperandsInstruction {
    public int index;

    public FLOAD_N(int cons_val){
        assert cons_val>=0 && cons_val <=3;
        index = cons_val;
    }

    @Override
    public void execute(StackFrame frame) {
        float val = frame.getLocalVars().getFloat(this.index);
        frame.getOperandStack().pushFloat(val);
    }
    public String toString(){
        return this.getClass().getSimpleName().replace("N",""+index);
    }
}
