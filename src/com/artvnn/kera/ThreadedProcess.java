package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/10/2017.
 */
public abstract class ThreadedProcess extends Thread implements IProcess {

    enum State {
        BORN, ALIVE, DEAD;
    }
    State state = State.BORN;

    IMessage message;

    public ThreadedProcess () {}

    int threadSleepInterval = 1000;
    @Override
    public void start (String name, int threadSleepInterval) {
        setName(name);
        this.threadSleepInterval = threadSleepInterval;
        start();
    }

    @Override
    public synchronized void sendMessage(IMessage message) {
        if(state != State.ALIVE) {
            Utils.log("Attempt to send sendMessage when thread is not ALIVE: " + state.toString() + ", message is " + message.toString());
        }
        this.message = message;
        interrupt();
    }

    @Override
    public void run () {
        boolean isDead = false;
        super.run();
        try {
            do {
                switch (state) {
                    case BORN:
                        onStart();
                        state = State.ALIVE;
                        break;
                    case ALIVE:
                        onProcess();
                        message = null;
                        // Lets be nice to others, go to sleep. Will be woken you if needed
                        try {
                            Thread.sleep(threadSleepInterval);
                        } catch (InterruptedException ex) {}
                        break;
                    case DEAD:
                        onStop();
                        isDead = true;
                        break;
                }
            } while (!isDead);
        } catch (Exception ex) {
            Utils.error("ThreadedProcess:run()", ex);
            if(message != null) {
                try {
                    vm.reply(message, "result", "ERROR: " + ex.getMessage());
                } catch (Exception err) {
                    Utils.error("!! ThreadedProcess:run()", err);
                }
                message = null;
            }
        }
    }

    @Override
    public void onStart() throws Exception {
        Utils.debug("ThreadedProcess:onStart()");
    }

    @Override
    public void onProcess() throws Exception {
        // Utils.debug("ThreadedProcess:onProcess()");
        if(message != null) {
            switch (message.getName()) {
                case "stop":
                    // Hasta la vista, baby!
                    state = State.DEAD;
                    break;
            }
        }
    }

    @Override
    public void onStop() throws Exception {
        Utils.debug("ThreadedProcess:onStop()");
    }

    IVirtualMachine vm = null;
    @Override
    public void setVM(IVirtualMachine vm) {
        this.vm = vm;
    }

}


