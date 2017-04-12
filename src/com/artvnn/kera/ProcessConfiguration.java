package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public class ProcessConfiguration {

    String name = "";
    public String getName (){
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }

    int poolSize = 5;
    public int getPoolSize () {
        return poolSize;
    }
    public void setPoolSize (int poolSize) {
        this.poolSize = poolSize;
    }
}
