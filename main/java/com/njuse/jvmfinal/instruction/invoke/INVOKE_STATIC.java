package com.njuse.jvmfinal.instruction.invoke;

import com.njuse.jvmfinal.instruction.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.MethodRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.data.Slot;

public class INVOKE_STATIC extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        JClass currentClazz = frame.getMethod().getClazz();
        Constant methodRef = currentClazz.getRuntimeConstantPool().getConstant(super.index);
        assert methodRef instanceof MethodRef;
        Method method = ((MethodRef) methodRef).resolveMethodRef();

        JClass targetClazz = method.getClazz();
        if(targetClazz.getInitState() == InitState.PREPARED){
            frame.setNextPC(frame.getNextPC() - 3);//opcode + operand = 3bytes
            targetClazz.initClass(frame.getThread(), targetClazz);
            return;
        }

        if(((MethodRef) methodRef).getClassName().contains("TestUtil")){
            if(method.getName().contains("equalInt")){
                int val2 = frame.getOperandStack().popInt();
                int val1 = frame.getOperandStack().popInt();
                if(val1 != val2){
                    throw new RuntimeException("" + val1 + "!="+val2 );
                }
                frame.getOperandStack().pushInt(val1);
                frame.getOperandStack().pushInt(val2);
            }else if(method.getName().contains("equalFloat")){
                float val2 = frame.getOperandStack().popFloat();
                float val1 = frame.getOperandStack().popFloat();
                if(val1 - val2 > 0.0001||val2 - val1 < -0.0001){
                    throw new RuntimeException();
                }
                frame.getOperandStack().pushFloat(val1);
                frame.getOperandStack().pushFloat(val2);
            }else if(method.getName().contains("reach")){
                int value = frame.getOperandStack().popInt();
                System.out.println(value);
                frame.getOperandStack().pushInt(value);
            }
        }




        int argc = method.getArgc();
        Slot[] argv = new Slot[argc];
        for (int i = 0; i < argc; i++) {
            argv[i] = frame.getOperandStack().popSlot();
        }
        StackFrame newFrame = createNewFrame(frame,argc,argv,method);

        frame.getThread().pushFrame(newFrame);

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
    private StackFrame createNewFrame(StackFrame frame, int argc, Slot[] argv, Method toInvoke){
        StackFrame newFrame = new StackFrame(frame.getThread(), toInvoke,
                toInvoke.getMaxStack(), toInvoke.getMaxLocal() + 1);// max_locals无需加1，因为不存在this的引用
        Vars vars = newFrame.getLocalVars();
        for(int i = 0; i < argc; i++){
            vars.setSlot(i, argv[argc - i - 1]);
        }
        return newFrame;
    }
}
