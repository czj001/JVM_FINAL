package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.array.IntArrayObject;

public class IASTORE extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        int value = stack.popInt();
        int index = stack.popInt();
        IntArrayObject arrRef = (IntArrayObject)stack.popObjectRef();
        if (arrRef == null) {
            throw new NullPointerException();
        } else if (index >= 0 && index < arrRef.getLen()) {
            arrRef.getArray()[index] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
