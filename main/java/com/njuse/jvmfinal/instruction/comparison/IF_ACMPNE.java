package com.njuse.jvmfinal.instruction.comparison;

import com.njuse.jvmfinal.runtime.data.JObject.JObject;

public class IF_ACMPNE extends IF_ACMPCOND{
    @Override
    protected boolean condition(JObject var1, JObject var2) {
        return !var1.equals(var2);
    }
}
