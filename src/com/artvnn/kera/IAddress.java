package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public interface IAddress {

    public String getVirtualMachineName ();
    public void setVirtualMachineName (String machine);

    public String getProcessName ();
    public void setProcessName (String process);

    public IAddress cloneIt () throws Exception;

    public void fromString (String address);

}
