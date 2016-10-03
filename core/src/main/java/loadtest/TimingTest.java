package loadtest;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.TimerTask;
 
public class TimingTest
{
    public static void checkSleepTest()
    {
        long lastTimestamp = System.currentTimeMillis();
        long lastNano = System.nanoTime();
 
        System.out.println("Start waiting with Thread.sleep(). Reset the system timestamp.");
        while (true)
        {
            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException exp)
            {
                System.err.println(exp);
            }
            long time = System.currentTimeMillis();
            long nano = System.nanoTime();
            Date date = new Date(time);
 
            long dtime = time - lastTimestamp;
            long dnano = nano - lastNano;
            System.out.println("Timer: " + time + " " + date);
            System.out.println("dMillis: " + dtime / 1000d + " dNanos: " + dnano / 1000000000d);
 
            lastTimestamp = time;
            lastNano = nano;
        }
 
    }
 
    public static void checkWaitTest()
    {
        Object lock = new Object();
        long lastTimestamp = System.currentTimeMillis();
        long lastNano = System.nanoTime();
 
        System.out.println("Start waiting with Object.wait(). Reset the system timestamp.");
        while (true)
        {
            synchronized (lock)
            {
                try
                {
                    lock.wait(5000);
                }
                catch (InterruptedException exp)
                {
                    System.err.println(exp);
                }
            }
            long time = System.currentTimeMillis();
            long nano = System.nanoTime();
            Date date = new Date(time);
 
            long dtime = time - lastTimestamp;
            long dnano = nano - lastNano;
            System.out.println("Timer: " + time + " " + date);
            System.out.println("dMillis: " + dtime / 1000d + " dNanos: " + dnano / 1000000000d);
 
            lastTimestamp = time;
            lastNano = nano;
        }
 
    }
 
    public static void startUtilTimerTest()
    {
        java.util.Timer timer = new java.util.Timer();
        TimerTask repeatTask = new TimerTask()
        {
            private long lastTimestamp = System.currentTimeMillis();
            private long lastNano = System.nanoTime();
 
            public void run()
            {
 
                long time = System.currentTimeMillis();
                long nano = System.nanoTime();
                Date date = new Date(time);
 
                long dtime = time - lastTimestamp;
                long dnano = nano - lastNano;
                System.out.println("Timer: " + time + " " + date);
                System.out.println("dMillis: " + dtime / 1000d + " dNanos: " + dnano / 1000000000d);
 
                lastTimestamp = time;
                lastNano = nano;
            }
 
        };
        timer.scheduleAtFixedRate(repeatTask, 1000, 5000);
 
        System.out.println("Timer has been started. Reset the system timestamp.");
    }
 
    public static void startSwingTimerTest()
    {
        ActionListener actionTask = new ActionListener()
        {
            private long lastTimestamp = System.currentTimeMillis();
            private long lastNano = System.nanoTime();
 
            public void actionPerformed(ActionEvent e)
            {
                long time = System.currentTimeMillis();
                long nano = System.nanoTime();
                Date date = new Date(time);
 
                long dtime = time - lastTimestamp;
                long dnano = nano - lastNano;
                System.out.println("Timer: " + time + " " + date);
                System.out.println("dMillis: " + dtime / 1000d + " dNanos: " + dnano / 1000000000d);
 
                lastTimestamp = time;
                lastNano = nano;
            }
 
        };
 
        javax.swing.Timer timer = new javax.swing.Timer(5000, actionTask);
        timer.start();
 
        System.out.println("Timer has been started. Reset the system timestamp.");
 
        // swing timer is a deamon thread => do something else to prevent the program to exit
        while (true)
        {
            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException exp)
            {
            }
        }
    }
 
    public static void main(String[] args)
    {
        //     startSwingTimerTest();
             startUtilTimerTest();
        //     checkSleepTest();
         //    checkWaitTest();
    } 
 
}
