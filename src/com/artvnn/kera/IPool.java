package com.artvnn.kera;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public interface IPool<Resource> {

    public void initialize (int poolSize, String name, Function<String, Resource> initializer);

    public Resource getResource ();

    public void releaseResource (Resource resource);

    /**
     * Terminates this pool
     * @param terminator "I'll be back!" ;) No way!
     */
    public void terminate (Consumer<Resource> terminator);

}
