package com.artvnn.kera;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public class Message implements IMessage {

    String name = "";
    @Override
    public String getName (){
        return name;
    }
    @Override
    public void setName (String name) {
        this.name = name;
    }

    Object data1 = null;
    @Override
    public Object getData1 () {
        return data1;
    }
    @Override
    public void setData1 (Object data1) {
        this.data1 = data1;
    }

    IAddress from = null;
    @Override
    public IAddress getFrom () {
        return from;
    }
    @Override
    public void setFrom (IAddress from) {
        this.from = from;
    }

    IAddress to = null;
    @Override
    public IAddress getTo () {
        return to;
    }
    @Override
    public void setTo (IAddress to) {
        this.to = to;
    }

    Object data2 = null;
    @Override
    public Object getData2 () {
        return data2;
    }
    @Override
    public void setData2 (Object data2) {
        this.data2 = data2;
    }

    IMessage context = null;
    @Override
    public IMessage getContext () {
        return context;
    }
    @Override
    public void setContext (IMessage context) {
        this.context = context;
    }

    public Message () throws Exception {
        from = (IAddress) DependencyInjector.createInstance("IAddress");
        to = (IAddress) DependencyInjector.createInstance("IAddress");
    }

    @Override
    public IMessage cloneIt () throws Exception {
        // TODO: Deep clone?
        IMessage cloned = (IMessage) DependencyInjector.createInstance("IMessage");
        cloned.setName(name);
        cloned.setData1(data1);
        cloned.setData2(data2);
        cloned.setContext(context);
        cloned.setFrom(from.cloneIt());
        cloned.setTo(to.cloneIt());
        return cloned;
    }

    @Override
    public String toString() {
        return "<< " + name + " | " + from.toString() + " => " + to.toString() + " >>";
    }
}
