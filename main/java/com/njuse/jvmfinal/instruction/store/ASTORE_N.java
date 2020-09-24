package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class ASTORE_N extends NoOperandsInstruction {
    private int index;

    public ASTORE_N(int cons_val){
        assert cons_val >=0 && cons_val <= 3;
        this.index = cons_val;
    }

    @Override
    public void execute(StackFrame frame) {
        JObject ref = frame.getOperandStack().popObjectRef();
        frame.getLocalVars().setObjectRef(this.index, ref);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().replace("N",""+index);
    }
}
