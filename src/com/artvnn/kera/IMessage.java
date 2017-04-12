package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public interface IMessage {

    public String getName ();
    public void setName (String name);

    public Object getData1 ();
    public void setData1 (Object data1);

    public IAddress getFrom ();
    public void setFrom (IAddress from);

    public IAddress getTo ();
    public void setTo (IAddress to);

    public IMessage getContext ();
    public void setContext (IMessage context);

    public Object getData2 ();
    public void setData2 (Object data2);

    public IMessage cloneIt () throws Exception;

}
