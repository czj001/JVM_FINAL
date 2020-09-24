package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import com.njuse.jvmfinal.runtime.data.JObject.array.RefArrayObject;


public class AALOAD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        RefArrayObject arrRef = (RefArrayObject)stack.popObjectRef();
        if (arrRef == null) {
            throw new NullPointerException();
        } else if (index >=0 && index < arrRef.getLen()) {
            stack.pushObjectRef(arrRef.getArray()[index]);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }

    }
}
