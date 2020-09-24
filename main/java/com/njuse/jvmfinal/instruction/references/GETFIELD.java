package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import com.njuse.jvmfinal.runtime.data.JObject.NonArrayObject;

public class GETFIELD extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        FieldRef fieldRef = (FieldRef) frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(super.index);
        Field field;
        field = fieldRef.resolveFieldRef();
        JClass targetClazz = field.getClazz();
        if(field.isStatic()){
            throw new IncompatibleClassChangeError();
        }
        OperandStack operandStack = frame.getOperandStack();
        NonArrayObject ref = (NonArrayObject) operandStack.popObjectRef();
        if(ref == null){
            throw new NullPointerException();
        }
        String descriptor = field.getDescriptor();
        int SlotID = field.getSlotID();
        Vars fields = ref.getInstanceFields();

        assert ref.getClazz().getName().equals(field.getClazz().getName());

        switch(descriptor.charAt(0)){
                case 'Z':
                case 'B':
                case 'C':
                case 'S':
                case 'I':
                    operandStack.pushInt(fields.getInt(SlotID));
                    break;
                case 'F':
                    operandStack.pushFloat(fields.getFloat(SlotID));
                    break;
                case 'J':
                    operandStack.pushLong(fields.getLong(SlotID));
                    break;
                case 'D':
                    operandStack.pushDouble(fields.getDouble(SlotID));
                    break;
                case 'L':
                case '[':
                    operandStack.pushObjectRef(fields.getObjectRef(SlotID));
                    break;
                default:
                    break;
        }

    }
}
