package com.artvnn.kera;

import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public class SimplePool<IProcess> implements IPool<IProcess> {

    public String name = "";

    // TODO: Use IQueue<IProcess> here rather than using Stack<IProcess>
    public Stack<IProcess> available = new Stack<>();

    public ArrayList<IProcess> busy = new ArrayList<>();

    @Override
    public void initialize (int poolSize, String name, Function<String, IProcess> initializer) {
        this.name = name;
        IntStream.range(0, poolSize).forEach((i) -> {
            Utils.log("Creating instance of " + name);
            IProcess process = initializer.apply(name);
            if(process != null) available.push(process);
        });
    }

    @Override
    public IProcess getResource () {
        if(available.isEmpty()) return null;
        IProcess process = available.pop();
        busy.add(process);
        return process;
    }

    @Override
    public void releaseResource (IProcess process) {
        available.push(process);
        busy.remove(process);
    }

    @Override
    public void terminate (Consumer<IProcess> terminator) {
        // Kill the busy ones
        busy.forEach((process) -> {
            terminator.accept(process);
        });
        busy.clear();
        // Kill free ones
        while(!available.isEmpty()) {
            terminator.accept(available.pop());
        }
    }
}
