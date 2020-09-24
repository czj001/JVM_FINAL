package com.njuse.jvmfinal.execution;

import com.njuse.jvmfinal.instruction.base.Instruction;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.nio.ByteBuffer;

public class Interpreter {
    private static ByteBuffer codeReader;



    public static void interpret(JThread thread){
        initCodeReader(thread);
        loop(thread);
        return;
    }
    private static void initCodeReader(JThread thread) {
        byte[] code = thread.getCurrentFrame().getMethod().getCode();
        codeReader = ByteBuffer.wrap(code);
        int nextPC = thread.getCurrentFrame().getNextPC();
        codeReader.position(nextPC);
    }
    private static void loop(JThread thread){
        while (true) {
            StackFrame currentTop = thread.getCurrentFrame();
            Method method = currentTop.getMethod();
            //set the reader's position to nextPC
            codeReader.position(currentTop.getNextPC());
            //fetch and decode
            int opcode = codeReader.get() & 0xff;
            Instruction instruction = Decoder.decode(opcode);
            instruction.fetchOperands(codeReader);
            //set nextPC to reader's position
            int nextPC = codeReader.position();
            currentTop.setNextPC(nextPC);
            instruction.execute(currentTop);

            //check whether there's a new frame
            //and whether there's more frame to exec
            StackFrame newTop = thread.getCurrentFrame();
            if (newTop == null) {
                return;
            }
            if (newTop != currentTop) {
                initCodeReader(thread);
            }
        }
    }
}
