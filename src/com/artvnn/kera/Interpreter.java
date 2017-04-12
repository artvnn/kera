package com.artvnn.kera;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by ARTVNN on 4/12/2017.
 */
public class Interpreter extends Function {

    @Override
    public void onProcess() throws Exception {
        super.onProcess();
        if(message != null) {
            Utils.debug("Interpreter message: " + message.toString());
            if(message.getName() == "result") {
                // Got result of a argument evaluation
                // Replace the evaluated argument
                String result = (String) message.getData1();
                // TODO: BAD! Exception handling has be redone.
                if(result.indexOf("ERROR:") == 0) {
                    // ERROR, return the error
                    functionReturn(message.getContext(), result);
                    return;
                } else {
                    ((ArrayList) message.getContext().getData1()).set(Integer.parseInt(message.getData2().toString()), result);
                    // Pop the stack
                    message = message.getContext();
                }
            }
            // Evaluate arguments, if needed
            ArrayList args = (ArrayList) message.getData1();
            boolean allArgumentsAreEvaluated = true;
            for(int i=0; i<args.size(); i++) {
                if(args.get(i) instanceof ArrayList) {
                    if(((ArrayList)args.get(i)).size()>0) {
                        // Needs to be evaluated
                        String functionName = (String) ((ArrayList) args.get(i)).get(0);
                        ((ArrayList) args.get(i)).remove(0);
                        functionCall(message, getName(), functionName, args.get(i), Integer.toString(i));
                        allArgumentsAreEvaluated = false;
                        break;
                    }
                }
            }
            if(allArgumentsAreEvaluated) {
                switch (message.getName()) {
                    case "echo":
                        functionReturn(message, args);
                        break;
                    case "quit":
                        forward(message, "stop", ((IProcess) vm).getName());
                        break;
                    case "+":
                        {
                            long calcResult = Long.parseLong((String)args.get(0));
                            for(int i=1; i<args.size(); i++) {
                                calcResult += Long.parseLong((String)args.get(i));
                            }
                            functionReturn(message, Long.toString(calcResult));
                        }
                        break;
                    case "-":
                        {
                            long calcResult = Long.parseLong((String)args.get(0));
                            for(int i=1; i<args.size(); i++) {
                                calcResult -= Long.parseLong((String)args.get(i));
                            }
                            functionReturn(message, Long.toString(calcResult));
                        }
                        break;
                    case "*":
                        {
                            long calcResult = Long.parseLong((String) args.get(0));
                            for (int i = 1; i < args.size(); i++) {
                                calcResult *= Long.parseLong((String) args.get(i));
                            }
                            functionReturn(message, Long.toString(calcResult));
                        }
                        break;
                    default:
                        {
                            functionReturn(message, "ERROR: Unknown function, " + message.getName());
                        }
                        break;
                }
            }
        }
    }
}
