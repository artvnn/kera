package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/10/2017.
 */
public interface IVirtualMachine {

    public void startVM (IConfiguration configuration) throws Exception;

    public IConfiguration getConfiguration ();

    public void createProcessPool (int poolSize, String name, IProcess process) throws Exception;

    public void createProcessPool (int poolSize, String name, IProcess process, int sleepInterval) throws Exception;

    public void send (String from, String to, String name, Object data1, Object data2, IMessage context) throws Exception;

    public void forward (IMessage message, String to, String name) throws Exception;

    public void reply (IMessage message, String name, Object data1) throws Exception;

}
