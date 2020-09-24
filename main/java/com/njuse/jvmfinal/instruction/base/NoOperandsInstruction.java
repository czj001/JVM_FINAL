package com.njuse.jvmfinal.instruction.base;

import java.nio.ByteBuffer;

public abstract class NoOperandsInstruction extends Instruction{
    @Override
    public void fetchOperands(ByteBuffer reader) {
        // do nothing
    }
}
