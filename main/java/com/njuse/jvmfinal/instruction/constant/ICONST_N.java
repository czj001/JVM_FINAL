package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class ICONST_N extends NoOperandsInstruction {
    public int value;
    public ICONST_N(int val){
        assert val >= -1 && val <=5;
        this.value = val;
    }

    @Override
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushInt(this.value);
    }

    @Override
    public String toString() {
        String suffix = (value == -1) ? "M1" : "" + value;
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + suffix;
    }
}
