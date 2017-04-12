package com.artvnn.kera;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by ARTVNN on 4/11/2017.
 */
public class CommandLine extends ThreadedProcess implements IExternalInterface {

    Parser parser = new Parser();
    ArrayList tree = null;
    boolean parsedFine = false;
    String commands = "";
    boolean iAmFree = true;
    Scanner scanner = new Scanner(System.in);

    private void print (String message) {
        System.out.print(message);
    }
    
    @Override
    public void onStart() throws Exception {
        super.onStart();
        print("\nWelcome to Kera!");
        print("\n");
        print("\nType in an S-Expression function call and press enter to evaluate it.");
        print("\n");
    }

    @Override
    public void onProcess() throws Exception {
        super.onProcess();
        if(message!=null) {
            switch(message.getName()) {
                case "result":
                    if(message.getData1() instanceof ArrayList) {
                        print("\nResult: " + Arrays.toString(((ArrayList) message.getData1()).toArray()) + "\n");
                    } else {
                        print("\nResult: " + message.getData1());
                    }
                    iAmFree = true;
                    break;
            }
        }
        if(iAmFree) {
            if(!parsedFine) {
                print("Format: (<functionName> <argument1> <argument2> ... )");
                print("\nFor example:");
                print("\n    (+ 5 4 3 2 1)");
                print("\n    (* 5 (* 4 (* 3 2 1)))");
                print("\n    (- 350 900 (* 5 6))");
                print("\n");
            }
            try {
                print("\n>> ");
                commands = scanner.nextLine();
                parsedFine = false;
                tree = parser.parse(commands); //'(' + commands + ')');
                if(tree.size()>0) parsedFine = true;
            } catch (Exception ex) {
                Utils.error(ex.getMessage(), ex);
            }
            if(parsedFine) {
                try {
                    print("Processing...\n");
                    vm.send(getName(), "_Interperter_","echo", tree, null, null);
                    iAmFree = false;
                } catch (Exception ex) {
                    Utils.error("(quit)", ex);
                }
            }
        }
    }

    @Override
    public void onStop() throws Exception {
        super.onStop();
        print("\n\nGoodbye!\n\n");
    }
}
