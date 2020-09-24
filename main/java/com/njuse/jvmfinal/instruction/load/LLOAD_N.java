package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LLOAD_N extends NoOperandsInstruction {
    public int index;

    public LLOAD_N(int cons_val){
        assert cons_val >= 0 && cons_val <= 3;
        index = cons_val;
    }

    @Override
    public void execute(StackFrame frame) {
        long val = frame.getLocalVars().getLong(this.index);
        frame.getOperandStack().pushLong(val);
    }
    public String toString(){
        return this.getClass().getSimpleName().replace("N",""+index);
    }
}
