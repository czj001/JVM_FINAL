package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class PUTSTATIC extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        Method currentMethod = frame.getMethod();
        JClass currentClazz = currentMethod.getClazz();
        RuntimeConstantPool currentRuntimeConstantPool = currentClazz.getRuntimeConstantPool();
        Constant fieldRef = currentRuntimeConstantPool.getConstant(this.index);
        assert fieldRef instanceof FieldRef;
        Field field = ((FieldRef)fieldRef).resolveFieldRef();
        JClass targetClazz = field.getClazz();
        if (targetClazz.getInitState() == InitState.PREPARED) {
            frame.setNextPC(frame.getNextPC() - 3);
            targetClazz.initClass(frame.getThread(), targetClazz);
            return;
        }

        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        if (field.isFinal() && (field.getClazz() != currentClazz || !currentMethod.getName().equals("<clinit>"))) {
            throw new IllegalAccessError();
        }

        String descriptor = field.getDescriptor();
        int slotID = field.getSlotID();
        Vars staticVars = targetClazz.getStaticVars();
        OperandStack stack = frame.getOperandStack();
        switch(descriptor.charAt(0)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z':
                    staticVars.setInt(slotID, stack.popInt());
                    break;
                case 'D':
                    staticVars.setDouble(slotID, stack.popDouble());
                    break;
                case 'E':
                case 'G':
                case 'H':
                case 'K':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                default:
                    break;
                case 'F':
                    staticVars.setFloat(slotID, stack.popFloat());
                    break;
                case 'J':
                    staticVars.setLong(slotID, stack.popLong());
                    break;
                case 'L':
                case '[':
                    staticVars.setObjectRef(slotID, stack.popObjectRef());
            }
    }
}
