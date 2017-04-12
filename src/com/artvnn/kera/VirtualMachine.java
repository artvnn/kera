package com.artvnn.kera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by ARTVNN on 4/10/2017.
 */
public class VirtualMachine extends ThreadedProcess implements IVirtualMachine {

    IConfiguration configuration;
    public IConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Object pool of available processes
     */
    HashMap<String, IPool<IProcess>> processPools = new HashMap<>();

    IQueue<IMessage> messageQueue;

    @Override
    public void startVM (IConfiguration configuration) throws Exception {
        Utils.log("Starting " + configuration.getName());
        this.configuration = configuration;
        DependencyInjector.setConfiguration(this.configuration);
        messageQueue = (IQueue<IMessage>) DependencyInjector.createInstance("IQueue");
        // The VM is also a process, it can accept messages
        createProcessPool(1,"_VM_", this);
        // Create other processes
        configuration.getProcesses().forEach( (processConfiguration) -> {
            try {
                createProcessPool(processConfiguration.getPoolSize(), processConfiguration.getName(), null);
            } catch (Exception ex) {
                Utils.error("Creation of process pool", ex);
            }
        });
    }

    @Override
    public void createProcessPool (int poolSize, String name, IProcess process) throws Exception {
        createProcessPool(poolSize, name, process, configuration.getThreadSleepInterval());
    }

    @Override
    public void createProcessPool (int poolSize, String name, IProcess process, int sleepInterval) throws Exception {
        IPool<IProcess> pool = (IPool<IProcess>) DependencyInjector.createInstance("IPool");
        pool.initialize(poolSize, name, (processName) -> {
            if(poolSize == 1 && process != null) {
                process.setVM(this);
                process.start(name, sleepInterval);
                return process;
            } else {
                try {
                    Utils.debug("Creating " + name);
                    IProcess newProcess = (IProcess) DependencyInjector.createInstance(name);
                    newProcess.setVM(this);
                    newProcess.start(name, sleepInterval);
                    return newProcess;
                } catch (Exception ex) {
                    Utils.error("VirtualMachine:createProcessPool()", ex);
                    return null;
                }
            }
        });
        processPools.put(name, pool);
    }

    ArrayList<IMessage> couldNotDeliver = new ArrayList<>();

    @Override
    public synchronized void onProcess() throws Exception {
        couldNotDeliver.clear();
        String processName = "";
        while(messageQueue.peek()!=null) {
            message = messageQueue.next();
            processName = message.getTo().getProcessName();
            if(processName.equals(getName())) {
                // For me
                // Utils.debug("My message: " + message.getName());
                switch (message.getName()) {
                    case "stop":
                        // Stop all processes
                        for (Map.Entry<String, IPool<IProcess>> pool : processPools.entrySet()) {
                            if(!pool.getValue().equals(this)) {
                                pool.getValue().terminate((process) -> {
                                    // TODO: This is cheating! Need to create a cloned message addressed to this process.
                                    // TODO: Some of these guys may ignore this message, they have to be TERMINATED!!
                                    process.sendMessage(message);
                                });
                            }
                        }
                        state = State.DEAD;
                        break;
                }
            } else {
                // For some other process
                // Utils.debug("Message for "+processName+": " + message.getName());
                IProcess process = processPools.get(processName).getResource();
                if(process==null) {
                    Utils.debug("No resource available for "+processName);
                    couldNotDeliver.add(message);
                } else {
                    // Deliver the message
                    try {
                        process.sendMessage(message);
                    } catch (Exception ex) {
                        Utils.error("Error processing message", ex);
                        reply(message, "result", "ERROR: " + ex.getMessage());
                    }
                    processPools.get(processName).releaseResource(process);
                }
            }
            message = null;
        }
        // Put all the messages that could not be delivered back into the queue.
        couldNotDeliver.forEach((msg) -> {
            messageQueue.add(msg);
        });
        message = null;
        super.onProcess();
    }

    @Override
    public synchronized void sendMessage(IMessage message) {
        // Put the message in queue
        messageQueue.add(message);
        // We will ignore this.message variable in onProcess, the messages will only be taken fro the queue
        super.sendMessage(message);
    }

    @Override
    public void send (String from, String to, String name, Object data1, Object data2, IMessage context) throws Exception {
        IMessage message = (IMessage) DependencyInjector.createInstance("IMessage");
        message.setName(name);
        message.setData1(data1);
        message.setData2(data2);
        message.setContext(context);
        message.getFrom().fromString(from);
        message.getTo().fromString(to);
        sendMessage(message);
    }

    @Override
    public void forward (IMessage message, String to, String name) throws Exception {
        IMessage forwardedMessage = message.cloneIt();
        if(name != null) forwardedMessage.setName(name);
        forwardedMessage.getTo().fromString(to);
        sendMessage(forwardedMessage);
    }

    @Override
    public void reply (IMessage message, String name, Object data1) throws Exception {
        send(message.getTo().toString(), message.getFrom().toString(), name, data1, message.getData2(), message.getContext());
    }
}
