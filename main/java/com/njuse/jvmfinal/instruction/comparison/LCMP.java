package com.njuse.jvmfinal.instruction.comparison;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LCMP extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        long val2 = frame.getOperandStack().popLong();
        long val1 = frame.getOperandStack().popLong();
        if(val1 > val2){
            frame.getOperandStack().pushInt(1);
        }else if(val1 < val2) {
            frame.getOperandStack().pushInt(-1);
        }else {
            frame.getOperandStack().pushInt(0);
        }
    }
}
