package com.njuse.jvmfinal.instruction.constant;

import com.njuse.jvmfinal.instruction.base.Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.nio.ByteBuffer;

public class SIPUSH extends Instruction {
    private short val;

    @Override
    public void fetchOperands(ByteBuffer reader) {
        this.val = reader.getShort();
    }

    @Override
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushInt(this.val);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " value: " + (int) this.val;
    }
}
