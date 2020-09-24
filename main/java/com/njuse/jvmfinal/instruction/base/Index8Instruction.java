package com.njuse.jvmfinal.instruction.base;

import java.nio.ByteBuffer;

public abstract class Index8Instruction extends Instruction{
    public int index; // unsigned byte

    @Override
    public void fetchOperands(ByteBuffer reader) {
        index = (int)reader.get() & 0xFF ;
    }
}
