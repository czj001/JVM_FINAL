package com.njuse.jvmfinal.instruction.base;

import java.nio.ByteBuffer;

public abstract class BranchInstruction extends Instruction{
    public int offset; // signed short

    @Override
    public void fetchOperands(ByteBuffer reader) {
        offset = reader.getShort();
    }
}
