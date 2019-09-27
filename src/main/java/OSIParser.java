public class OSIParser {
    public static void main(String[] args) throws java.io.IOException {
        byte[] verbBytes = new byte[4];
        System.in.read(verbBytes);
        String verb = new String(verbBytes);
        System.out.println("Verb: '" + verb + "'");

        StringBuilder tagBuilder = new StringBuilder();

        byte[] current = new byte[1];
        int last = System.in.read(current);
        while(last > -1) {
            if (current[0] == 0x00) {
                break;
            }
            tagBuilder.append(new String(current));

            last = System.in.read(current);
        }

        System.out.println("Tags: '" + tagBuilder + "'");

        for (int i = 0; i < tagBuilder.length(); i++) {
            char tag = tagBuilder.charAt(i);
            if (tag == 'i') {
                System.in.read(current);
                System.out.println("Attribute " + i + " (int32): " + current[0]);
            } else if (tag == 's') {
                StringBuilder attributeBuilder = new StringBuilder();
                current = new byte[1];
                last = System.in.read(current);
                while(last > -1) {
                    if (current[0] == 0x00) {
                        break;
                    }
                    attributeBuilder.append(new String(current));

                    last = System.in.read(current);
                }
                System.out.println("Attribute " + i + " (string): " + attributeBuilder);
            }
        }
    }
}