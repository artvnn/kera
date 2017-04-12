package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public abstract class Function extends ThreadedProcess {

    @Override
    public void onProcess() throws Exception {
        super.onProcess();
    }

    protected void functionCall (IMessage message, String to, String name, Object arguments, Object state) throws Exception {
        vm.send(message.getTo().toString(), to, name, arguments, state, message);
    }

    protected void functionReturn (IMessage message, Object result) throws Exception {
        vm.reply(message, "result", result);
    }

    protected void recurse (IMessage message, Object arguments, Object state) throws Exception {
        functionCall(message, getName(), message.getName(), arguments, state);
    }

    // Proxies, syntax sugar
    public void send (String from, String to, String name, Object data1, Object data2, IMessage context) throws Exception {
        vm.send(from, to, name, data1, data2, context);
    }

    public void forward (IMessage message, String to, String name) throws Exception {
        vm.forward(message, to, name);
    }

    public void reply (IMessage message, String name, Object data1) throws Exception {
        vm.reply(message, name, data1);
    }

}
