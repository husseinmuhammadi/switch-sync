package test;

import org.jpos.iso.*;

import javax.xml.bind.ValidationEvent;
import java.lang.management.BufferPoolMXBean;

public class BillPaymentAdditionalDataPrivate {

    static ISOPackager packager = new BPF48Packager();

    public static void main(String[] args) {
        try {
            String value = "00000000        GA006212290689806234074200850";

            value = pack();

            ISOMsg field48 = new ISOMsg();
            field48.setPackager(packager);
            byte[] bytes = value.getBytes();
            field48.unpack(bytes);

            System.out.println(field48.getValue(0));
            System.out.println(field48.getValue(1));
            System.out.println(field48.getValue(2));
            System.out.println(field48.getValue(3));
            System.out.println(field48.getValue(4));
            System.out.println(field48.getValue(5));
            System.out.println(field48.getValue(6));


        } catch (ISOException e) {
            e.printStackTrace();
        }

    }

    private static String pack() throws ISOException {
        ISOMsg field48 = new ISOMsg();
        field48.setPackager(packager);

        field48.set(0, "5454"); // --> "5454  "
        field48.set(1, "00");
        field48.set(2, "        ");
        field48.set(3, "EL");
        field48.set(4, "62"); // --> 0062
        field48.set(5, "12345678");
        field48.set(6, "0012");
        byte[] bytes = field48.pack();
        String result = new String(bytes);

        System.out.println(result);
        return result;
    }
}
