package com.njuse.jvmfinal.instruction.base;

import java.nio.ByteBuffer;

public abstract class Index16Instruction extends Instruction{
    public int index; // unsigned short

    @Override
    public void fetchOperands(ByteBuffer reader) {
        index = (int) reader.getShort() & 0xffff;
    }
}
