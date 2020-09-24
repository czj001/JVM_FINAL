package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class ARRAYLENGTH extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        JObject ref = stack.popObjectRef();
        if (ref != null && !ref.isNull()) {
            stack.pushInt(((ArrayObject)ref).getLen());
        } else {
            throw new NullPointerException();
        }
    }
}
