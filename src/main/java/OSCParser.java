import java.util.ArrayList;
import java.util.logging.Logger;

public class OSCParser {
    public static void main(String[] args) throws java.io.IOException {
        Logger log = Logger.getLogger("Main");

        int verbSize = 4;

        // Verb
        byte[] verbBytes = new byte[verbSize];
        System.in.read(verbBytes);
        StringBuilder verbBuilder = new StringBuilder();
        for (int i = 0; i < verbSize; i++) {
            byte b = verbBytes[i];
            verbBuilder.append((char) b);
        }
        log.info("Verb: " + verbBuilder);

        // Tags
        ArrayList<Integer> parseInstructions = new ArrayList<Integer>();
        byte[] current = new byte[1];
        int last = System.in.read(current);
        while(last > -1) {
            if (current[0] == 0x00) {
                break;
            }
            parseInstructions.add((int) current[0]);

            last = System.in.read(current);
        }

        // Payload
        log.info("Tags: " + parseInstructions);
        int tag;
        for (int i = 0; i < parseInstructions.size(); i++) {
            tag = parseInstructions.get(i);
            if (tag == 105) {
                System.in.read(current);
                log.info("Attribute " + i + " (int32): " + current[0]);
            } else if (tag == 115) {
                StringBuilder attributeBuilder = new StringBuilder();
                current = new byte[1];
                last = System.in.read(current);
                while(last > -1) {
                    if (current[0] == 0x00) {
                        break;
                    }
                    attributeBuilder.append(Character.toString(current[0]));

                    last = System.in.read(current);
                }
                log.info("Attribute " + i + " (int32): " + attributeBuilder);
            }
        }
    }
}