package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public class Address implements IAddress {

    public Address () {}

    private String virtualMachineName = "";
    @Override
    public String getVirtualMachineName() {
        return virtualMachineName;
    }
    @Override
    public void setVirtualMachineName(String virtualMachineName) {
        this.virtualMachineName = virtualMachineName;
    }

    private String processName = "";
    @Override
    public String getProcessName() {
        return processName;
    }
    @Override
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public String toString() {
        return "@" + (virtualMachineName.equals("") ? "this" : virtualMachineName) + ":" + processName;
    }

    @Override
    public IAddress cloneIt () throws Exception {
        IAddress cloned = (IAddress) DependencyInjector.createInstance("IAddress");
        cloned.setVirtualMachineName(virtualMachineName);
        cloned.setProcessName(processName);
        return cloned;
    }

    public void fromString (String address) {
        if(address.indexOf(':')<0) address = ':' + address;
        String[] temp = address.split("\\:");
        setProcessName(temp[1]);
        setVirtualMachineName(temp[0]);
    }

}
