package com.njuse.jvmfinal.instruction.comparison;

public class IFGE extends IFCOND{
    @Override
    protected boolean condition(int value) {
        return value >= 0;
    }
}
