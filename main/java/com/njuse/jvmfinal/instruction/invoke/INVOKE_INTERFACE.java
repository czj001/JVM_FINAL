package com.njuse.jvmfinal.instruction.invoke;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.InterfaceMethodRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import com.njuse.jvmfinal.runtime.data.Slot;

import java.nio.ByteBuffer;

public class INVOKE_INTERFACE extends Index16Instruction {

    @Override
    public void fetchOperands(ByteBuffer reader) {
        super.fetchOperands(reader);
        int count = reader.get() & 0xff;
        assert count != 0;
        int zero = reader.get() &0xff;
        assert zero == 0;
    }

    @Override
    public void execute(StackFrame frame) {
        JClass currentClazz = frame.getMethod().getClazz();
        Constant interfaceMethodRed = currentClazz.getRuntimeConstantPool().getConstant(super.index);
        assert interfaceMethodRed instanceof InterfaceMethodRef;
        Method method = ((InterfaceMethodRef)interfaceMethodRed).resolveInterfaceMethodRef();

        int argc = method.getArgc();
        Slot[] argv = new Slot[argc];
        for (int i = 0; i < argc; i++) {
            argv[i] = frame.getOperandStack().popSlot();
        }
        JObject objectRef = frame.getOperandStack().popObjectRef();
        JClass clazz = objectRef.getClazz();
        Method toInvoke = ((InterfaceMethodRef)interfaceMethodRed).resolveInterfaceMethodRef(clazz);

        StackFrame newStackFrame = preNewStackFrame(frame,argc,argv,objectRef,toInvoke);

        frame.getThread().pushFrame(newStackFrame);

        if (method.isNative()) {
            if (method.getName().equals("registerNatives")) {
                frame.getThread().popFrame();
            } else {
                System.out.println("Native method:"
                        + method.getClazz().getName()
                        + method.name
                        + method.descriptor);
                frame.getThread().popFrame();
            }
        }
    }
    private StackFrame preNewStackFrame(StackFrame frame, int argc, Slot[] argv, JObject objectRef, Method toInvoke){
        StackFrame newFrame = new StackFrame(frame.getThread(), toInvoke,
                toInvoke.getMaxStack(), toInvoke.getMaxLocal() + 1);
        Vars localVars = newFrame.getLocalVars();
        Slot thisSlot = new Slot();
        thisSlot.setObject(objectRef);
        localVars.setSlot(0, thisSlot);
        for (int i = 1; i < argc + 1; i++) {
            localVars.setSlot(i, argv[argc - i]);
        }
        return newFrame;
    }
}
