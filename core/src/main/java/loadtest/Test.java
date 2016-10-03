package loadtest;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class Test {
	public static void main(String[] args) throws NamingException {
		/*
		 * SecureRandom randomKey = new SecureRandom();
		 * System.out.println(randomKey.nextLong());
		 */
//		Hello hello = null;
//		hello.printHello();
		//ResourceBundle bundle = ResourceBundle.getBundle("dm");
		 Properties env = new Properties();
         env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
         env.put(Context.PROVIDER_URL, "t3://localhost" + ":" + 7001);
         
         NamingEnumeration enum1 =  new InitialContext(env).list("");
         while (enum1.hasMore()) {
        	    NameClassPair nc = (NameClassPair)enum1.next();
        	    System.out.println(nc.getName());
        	}
	}

}

class Hello {
	static void printHello() {

		System.out.println("Hello !");

	}
}