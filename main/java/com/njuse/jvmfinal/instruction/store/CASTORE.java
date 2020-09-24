package com.njuse.jvmfinal.instruction.store;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.array.CharArrayObject;

public class CASTORE extends NoOperandsInstruction {

    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        char val = (char)stack.popInt();
        int index = stack.popInt();
        CharArrayObject arrRef = (CharArrayObject)stack.popObjectRef();
        if (arrRef == null) {
            throw new NullPointerException();
        } else if (index >= 0 && index < arrRef.getLen()) {
            arrRef.getArray()[index] = val;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

}
