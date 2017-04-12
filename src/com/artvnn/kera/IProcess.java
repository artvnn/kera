package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/10/2017.
 */
public interface IProcess {

    public void setVM (IVirtualMachine vm);

    public void start (String name, int threadSleepInterval);

    public void sendMessage(IMessage message);

    public void onStart () throws Exception;

    public void onProcess () throws Exception;

    public void onStop () throws Exception;

    public String getName ();

}
