package com.njuse.jvmfinal.instruction.comparison;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FCMPL extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        float value2 = frame.getOperandStack().popFloat();
        float value1 = frame.getOperandStack().popFloat();
        if (!Float.isNaN(value1) && !Float.isNaN(value2)) {
            frame.getOperandStack().pushInt(Float.compare(value1, value2));
        } else {
            frame.getOperandStack().pushInt(-1);
        }
    }
}
