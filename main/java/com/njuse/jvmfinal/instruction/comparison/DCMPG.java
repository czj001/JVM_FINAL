package com.njuse.jvmfinal.instruction.comparison;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DCMPG extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack os = frame.getOperandStack();
        double var2 = os.popDouble();
        double var1 = os.popDouble();
        if(Double.isNaN(var1) || Double.isNaN(var2)){
            os.pushInt(1);
        } else if(var1 > var2){
            os.pushInt(1);
        }else if(var1 < var2){
            os.pushInt(-1);
        }else {
            os.pushInt(0);
        }
    }
}
