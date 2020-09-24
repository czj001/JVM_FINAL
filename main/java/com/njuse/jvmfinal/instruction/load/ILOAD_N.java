package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class ILOAD_N extends NoOperandsInstruction {
    public int index;

    public ILOAD_N(int cons_val){
        assert (cons_val >= 0 && cons_val <= 3);
        this.index = cons_val;
    }


    @Override
    public void execute(StackFrame frame) {
        int val = frame.getLocalVars().getInt(this.index);
        frame.getOperandStack().pushInt(val);
    }
    public String toString(){
        return this.getClass().getSimpleName().replace("N",""+index);
    }
}
