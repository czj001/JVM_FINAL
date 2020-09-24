package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.array.IntArrayObject;

public class IALOAD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        IntArrayObject arrRef = (IntArrayObject)stack.popObjectRef();
        if (arrRef == null) {
            throw new NullPointerException();
        } else if (this.checkIndex(arrRef.getLen(), index)) {
            stack.pushInt(arrRef.getArray()[index]);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private boolean checkIndex(int len, int index) {
        return index >= 0 && index < len;
    }
}
