package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.nio.ByteBuffer;

public class BIPUSH extends Instruction {
    private byte val;

    public void fetchOperands(ByteBuffer reader) {
        this.val = reader.get();
    }
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushInt(this.val);
    }
    public String toString() {
        return this.getClass().getSimpleName() + " value : " + this.val;
    }
}
