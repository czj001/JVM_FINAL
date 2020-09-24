package com.njuse.jvmfinal.runtime;

import com.njuse.jvmfinal.memory.jclass.Method;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StackFrame {
    private OperandStack operandStack;
    private Vars localVars;
    private int nextPC;
    // do not have to record the return address, each StackFrame has it own PC;
    private Method method; // for dynamic linking
    private JThread thread;// for multiple thread

    public StackFrame(JThread jThread, Method method, int maxStack, int maxLocal){
        operandStack = new OperandStack(maxStack);
        localVars = new Vars(maxLocal);
        nextPC = 0;
        this.method = method;
        this.thread = jThread;
    }


}
