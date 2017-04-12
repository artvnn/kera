package com.artvnn.kera;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.*;

/**
 * Created by ARTVNN on 4/10/2017.
 */
public class JsonConfiguration implements IConfiguration {

    private JsonConfiguration () {}

    public static JsonConfiguration getInstance (String file) {
        Utils.log("Loading configuration file: " + file);
        JsonObject configJson = (new JsonParser()).parse(Utils.readTextFile(file)).getAsJsonObject();
        return  (new Gson()).fromJson(configJson, JsonConfiguration.class);
    }

    @Override
    public String getType() {
        return "Java";
    }

    String name;
    @Override
    public String getName() {
        return name;
    }

    HashMap<String, String> dependencies = new HashMap<String, String>();
    @Override
    public HashMap<String, String> getDependencies() {
        return dependencies;
    }

    int threadSleepInterval;
    @Override
    public int getThreadSleepInterval () {
        return threadSleepInterval;
    }

    ArrayList<ProcessConfiguration> processes = new ArrayList<>();
    @Override
    public ArrayList<ProcessConfiguration> getProcesses () {
        return processes;
    }

}
