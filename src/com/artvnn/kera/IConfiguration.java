package com.artvnn.kera;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ARTVNN on 4/10/2017.
 */
public interface IConfiguration {

    public String getType ();

    public String getName ();

    public HashMap<String, String> getDependencies ();

    public ArrayList<ProcessConfiguration> getProcesses ();

    public int getThreadSleepInterval ();

}
