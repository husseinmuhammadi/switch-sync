package test;

import org.jpos.iso.IFA_LLNUM;
import org.jpos.iso.IFA_NUMERIC;
import org.jpos.iso.IF_CHAR;
import org.jpos.iso.ISOFieldPackager;

public class BPF48Packager extends F48Packager {
    protected ISOFieldPackager fld[] = {
    	    /*000*/ new IF_CHAR     (  6, "RESERVED"),   // ans 6
    	    /*001*/ new IFA_NUMERIC (  2, "LANGUAGE"),   // n2
    	    /*002*/ new IF_CHAR     (  8, "RESERVED"),   // ans 8
    	    /*003*/ new IF_CHAR     (  2, "BILL TYPE"),  // a 2 ?
    	    /*004*/ new IFA_NUMERIC (  4, "COMPANY ID"), // n 4
    	    /*005*/ new IFA_LLNUM   ( 18, "BILL ID"),    // n ..18
    	    /*006*/ new IFA_LLNUM   ( 18, "PAY ID"),     // n ..18
    };

    public BPF48Packager() {
        super();
        setFieldPackager(fld);
    }
}
