package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import com.njuse.jvmfinal.runtime.data.JObject.NonArrayObject;

public class PUTFIELD extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        FieldRef fieldRef = (FieldRef) frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(super.index);
        Field field;
        field = fieldRef.resolveFieldRef();
        JClass targetClazz = field.getClazz();
        if(field.isStatic()){
            throw new IncompatibleClassChangeError();
        }
        if(field.isFinal()){
            if(field .getClazz()!= frame.getMethod().getClazz()|| !frame.getMethod().getName().equals("<init>")){
                throw new IllegalAccessError();
            }
        }

        String descriptor = field.getDescriptor();
        int SlotID = field.getSlotID();
        OperandStack operandStack = frame.getOperandStack();
        JObject ref;
        switch (descriptor.charAt(0)){
                case 'Z':
                case 'B':
                case 'C':
                case 'S':
                case 'I':
                    int intVal = operandStack.popInt();
                    ref = operandStack.popObjectRef();
                    checkRed(ref);
                    ((NonArrayObject) ref).getInstanceFields().setInt(SlotID,intVal);
                    break;
                case 'F':
                    float floatVal = operandStack.popFloat();
                    ref = operandStack.popObjectRef();
                    checkRed(ref);
                    ((NonArrayObject) ref).getInstanceFields().setFloat(SlotID,floatVal);
                    break;
                case 'J':
                    long longVal = operandStack.popLong();
                    ref = operandStack.popObjectRef();
                    checkRed(ref);
                    ((NonArrayObject) ref).getInstanceFields().setLong(SlotID,longVal);
                    break;
                case 'D':
                    double doubleVal = operandStack.popDouble();
                    ref = operandStack.popObjectRef();
                    checkRed(ref);
                    ((NonArrayObject) ref).getInstanceFields().setDouble(SlotID,doubleVal);
                    break;
                case 'L':
                case '[':
                    JObject obVal = operandStack.popObjectRef();
                    ref = operandStack.popObjectRef();
                    checkRed(ref);
                    ((NonArrayObject) ref).getInstanceFields().setObjectRef(SlotID,obVal);
                    break;
                default:
                    break;
        }
    }
    private void checkRed(JObject ref){
        if(ref == null){
            throw new NullPointerException();
        }
    }
}
