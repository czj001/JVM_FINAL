package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DLOAD_N extends NoOperandsInstruction {
    public int index;
    public DLOAD_N(int cons_val){
        assert (cons_val >= 0 && cons_val <= 3);
        this.index = cons_val;
    }

    @Override
    public void execute(StackFrame frame) {
        double val = frame.getLocalVars().getDouble(this.index);
        frame.getOperandStack().pushDouble(val);
    }
    public String toString(){
        return this.getClass().getSimpleName().replace("N",""+index);
    }
}
