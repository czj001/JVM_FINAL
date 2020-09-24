package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class GETSTATIC extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        FieldRef fieldRef = (FieldRef) runtimeConstantPool.getConstant(index);
        Field field;
        field = fieldRef.resolveFieldRef();
        JClass targetClazz = field.getClazz();

        //check class whether init
        if (targetClazz.getInitState() == InitState.PREPARED) {
            frame.setNextPC(frame.getNextPC() - 3);//opcode + operand = 3bytes
            targetClazz.initClass(frame.getThread(), targetClazz);
            return;
        }

        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        String descriptor = field.getDescriptor();
        int slotID = field.getSlotID();
        Vars staticVars = targetClazz.getStaticVars();
        OperandStack stack = frame.getOperandStack();
        switch (descriptor.charAt(0)) {
                case 'Z':
                case 'B':
                case 'C':
                case 'S':
                case 'I':
                    stack.pushInt(staticVars.getInt(slotID));
                    break;
                case 'F':
                    stack.pushFloat(staticVars.getFloat(slotID));
                    break;
                case 'J':
                    stack.pushLong(staticVars.getLong(slotID));
                    break;
                case 'D':
                    stack.pushDouble(staticVars.getDouble(slotID));
                    break;
                case 'L':
                case '[':
                    stack.pushObjectRef(staticVars.getObjectRef(slotID));
                    break;
                default:
                    break;
        }
    }
}
