package com.artvnn.kera;

import java.lang.reflect.Constructor;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public class DependencyInjector {

    static IConfiguration configuration = null;
    public static void setConfiguration (IConfiguration _congifuration) {
        configuration = _congifuration;
    }

    /**
     * Used for Dependency Injection.
     * The IDs and their Class names are taken from the configuration object.
     * @param interfaceName
     * @return Created object (instance)
     * @throws Exception
     */
    public static Object createInstance (String interfaceName) throws Exception {
        String className = "com.artvnn.kera." + configuration.getDependencies().get(interfaceName);
        try {
            Class<?> implementation = Class.forName(className);
            Constructor<?> constructor = implementation.getConstructor();
            return constructor.newInstance();
        } catch (Exception ex) {
            Utils.error("VirtualMachine:createInstance() " + ex.getMessage(), ex);
            throw ex;
        }
    }

}
