package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class ALOAD_N extends NoOperandsInstruction {
    public int index;

    public ALOAD_N(int cons_val){
        assert cons_val >=0 && cons_val <=3;
        index = cons_val;
    }

    @Override
    public void execute(StackFrame frame) {
        JObject ref = frame.getLocalVars().getObjectRef(this.index);
        frame.getOperandStack().pushObjectRef(ref);
    }
    public String toString(){
        return this.getClass().getSimpleName().replace("N",""+index);
    }
}
