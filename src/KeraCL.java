import com.artvnn.kera.*;

public class KeraCL {

    public static void main(String[] args) {

        try {
            // Start the VM
            IVirtualMachine vm = new VirtualMachine();
            vm.startVM(JsonConfiguration.getInstance(Utils.getCurrentPath() + "/config.json"));
        } catch (Exception ex) {
            Utils.error("KeraCL", ex);
        }

    }
}
