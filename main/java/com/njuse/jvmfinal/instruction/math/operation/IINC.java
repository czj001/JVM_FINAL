package com.njuse.jvmfinal.instruction.math.operation;

import com.njuse.jvmfinal.instruction.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.nio.ByteBuffer;

public class IINC extends Index8Instruction {
    public int cons;

    @Override
    public void fetchOperands(ByteBuffer reader) {
        this.index = (int)reader.get() & 0xff;
        this.cons = reader.get();
    }

    @Override
    public void execute(StackFrame frame) {
        int val = frame.getLocalVars().getInt(this.index);
        frame.getLocalVars().setInt(this.index, val + this.cons);
    }
    public String toString(){
        return this.getClass().getSimpleName() + " index : " + this.index + "const : " + this.cons;
    }
}
