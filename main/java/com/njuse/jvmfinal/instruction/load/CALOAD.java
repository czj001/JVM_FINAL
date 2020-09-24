package com.njuse.jvmfinal.instruction.load;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.array.CharArrayObject;

public class CALOAD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        int index =stack.popInt();
        CharArrayObject arrayRef = (CharArrayObject) stack.popObjectRef();
        if(arrayRef == null){
            throw new NullPointerException();
        }else if(index >=0 && index < arrayRef.getLen()){
            stack.pushInt(arrayRef.getArray()[index]);
        }else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
