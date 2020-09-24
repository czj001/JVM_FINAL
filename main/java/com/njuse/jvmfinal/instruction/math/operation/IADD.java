package com.njuse.jvmfinal.instruction.math.operation;

import com.njuse.jvmfinal.instruction.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class IADD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        //读取操作数栈
        OperandStack stack = frame.getOperandStack();
        //读取第二个操作数
        //（注意"栈"这个数据结构是后进先出的，所以第二个操作数先被读取）
        int val2 = stack.popInt();
        //读取第一个操作数
        int val1 = stack.popInt();
        //计算结果
        int res = val1 + val2;
        //将结果放入操作数栈
        stack.pushInt(res);
    }

}
