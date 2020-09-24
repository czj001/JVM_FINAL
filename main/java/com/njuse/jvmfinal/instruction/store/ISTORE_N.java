package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class ISTORE_N extends NoOperandsInstruction {

    public int index;
    public ISTORE_N(int cons_val){
        assert cons_val >=0 && cons_val <=3;
        index = cons_val;
    }

    @Override
    public void execute(StackFrame frame) {
        int val = frame.getOperandStack().popInt();
        frame.getLocalVars().setInt(this.index,val);
    }
    public String toString(){
        return this.getClass().getSimpleName().replace("N",""+index);
    }
}
