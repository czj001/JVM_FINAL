package com.njuse.jvmfinal.runtime;

public class JThread {
    private ThreadStack threadStack;

    public JThread(){
        threadStack = new ThreadStack();
    }

    public void pushFrame(StackFrame stackFrame){

        threadStack.pushFrame(stackFrame);
    }

    public void popFrame(){

        threadStack.popFrame();
    }

    public StackFrame getCurrentFrame(){
        return threadStack.getCurrentFrame();
    }
}
