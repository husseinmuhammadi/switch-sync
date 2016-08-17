package org.jpos.mbean;

import javax.management.*;

import org.jboss.logging.Logger;
import org.jboss.system.ServiceMBeanSupport;
import org.jpos.q2.Q2;

public class Q2Service extends ServiceMBeanSupport
        implements Q2ServiceMBean, Runnable {

    private Q2 q2Server;

    public Q2Service() {
        q2Server = null;
    }

    protected void startService()
            throws Exception {
        super.startService();
        super.log.info("Q2Service starting ...");
        String deployPath[] = {
                "-d", "q2/deploy"
        };
        q2Server = new Q2(deployPath);
        (new Thread(this)).start();
        log.info("Q2Service started.");
    }

    public void run() {
        try {
            q2Server.start();
        } finally {
        }
        /*
        catch(MalformedObjectNameException e)
        {
            e.printStackTrace();
        }
        catch(InstanceAlreadyExistsException e)
        {
            e.printStackTrace();
        }
        catch(NotCompliantMBeanException e)
        {
            e.printStackTrace();
        }
        catch(MBeanRegistrationException e)
        {
            e.printStackTrace();
        }
		
		*/
    }

    protected void stopService()
            throws Exception {
        super.stopService();
        super.log.info("Q2Service stopping ...");
        q2Server.shutdown();
        super.log.info("Q2Service stopped.");
    }
}
