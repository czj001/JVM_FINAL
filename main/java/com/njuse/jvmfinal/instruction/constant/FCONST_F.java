package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FCONST_F extends NoOperandsInstruction {
    private float val;

    private void check(float f){
        assert f == 0.0f || f == 1.0f || f == 2.0f;
    }

    public FCONST_F(float f){
        check(f);
        this.val = f;
    }

    @Override
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushFloat(this.val);
    }
}
