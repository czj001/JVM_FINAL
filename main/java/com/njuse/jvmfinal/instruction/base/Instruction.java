package com.njuse.jvmfinal.instruction.base;

import com.njuse.jvmfinal.runtime.StackFrame;

import java.nio.ByteBuffer;

public abstract class Instruction {
    public abstract void execute(StackFrame frame);
    public abstract void fetchOperands(ByteBuffer reader);
    public String getInstructionName(){
        return this.toString();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
