package com.njuse.jvmfinal.runtime;

import java.util.EmptyStackException;
import java.util.Stack;

public class ThreadStack {

    private static int maxSize = 64*1024;
    private Stack<StackFrame> stackFrames;
    private int currentSize;


    public ThreadStack(){
        stackFrames = new Stack<>();
        currentSize = 0;
    }
    public void pushFrame(StackFrame frame) {
        if (currentSize >= maxSize) {
            throw new StackOverflowError();
        }
        stackFrames.push(frame);
        currentSize++;
    }
    public void popFrame() {
        if (currentSize == 0) {
            throw new EmptyStackException();
        }
        stackFrames.pop();
        currentSize--;
    }
    public StackFrame getCurrentFrame() {
        if (currentSize == 0) return null;
        return stackFrames.lastElement();
    }
}
