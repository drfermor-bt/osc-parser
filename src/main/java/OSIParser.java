import java.util.Vector;

public class OSIParser {
    public static void main(String[] args) throws java.io.IOException {
        byte[] verbBytes = new byte[4];
        System.in.read(verbBytes);
        String verb = new String(verbBytes);
        System.out.printf("Verb: '%s'\n", verb);

        Vector<Integer> parseInstructions = new Vector<Integer>();
        byte[] current = new byte[1];
        int last = System.in.read(current);
        while(last > -1) {
            if (current[0] == 0x00) {
                break;
            }
            parseInstructions.add((int) current[0]);

            last = System.in.read(current);
        }

        System.out.printf("Tags: '%s'\n", parseInstructions);
        int tag;
        for (int i = 0; i < parseInstructions.size(); i++) {
            tag = parseInstructions.elementAt(i);
            if (tag == 105) {
                System.in.read(current);
                System.out.printf("Attribute %d (int32): %s\n", i, current[0]);
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
                System.out.printf("Attribute %d (string): %s\n", i, attributeBuilder);
            }
        }
    }
}