package loadtest;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.MUX;
import org.jpos.q2.iso.QMUX;
import org.perf4j.StopWatch;

import com.en.loaddata.CardDetails;
import com.en.loaddata.LoadData;

public class MyListenerGenerator extends JFrame implements ActionListener{

  private static final long serialVersionUID = -4201803498634433156L;
  
  JTextArea _resultArea = new JTextArea(6, 20);
    JButton button=new JButton("LoadTest");
    JTextField txtNo=new JTextField("60");
    JTextField txtTPS=new JTextField("30"); 
    JTextField txtGAP=new JTextField("10");   
    ISOSource isoSource = null;
    public MyListenerGenerator(ISOSource isoSource) {
      super("Load Sender");
      this.isoSource = isoSource;
        JScrollPane scrollingArea = new JScrollPane(_resultArea);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        button.addActionListener(this);
        JPanel control=new JPanel();
        control.setLayout(new GridLayout(3,2));
        control.add(new JLabel("Transaction Count:"));
        control.add(txtNo);
        control.add(new JLabel("TPS:"));
        control.add(txtTPS);
        control.add(new JLabel("GAP:"));
        control.add(txtGAP);
        content.add(button,BorderLayout.NORTH);
        content.add(control, BorderLayout.CENTER);
        content.add(scrollingArea, BorderLayout.SOUTH);
       //... Set window characteristics.
        this.setContentPane(content);
        this.setTitle("Load Sender");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.pack();
    }
    
    //============================================================= main
    public static void main(String[] args) {
        JFrame win = new LoadTestUI("Example");
        win.setVisible(true);
    }

 
  public void actionPerformed(ActionEvent event) {
    
    try{
      int txn=Integer.parseInt(txtNo.getText());
      int tps=Integer.parseInt(txtTPS.getText());
      int gap=Integer.parseInt(txtGAP.getText());
      generateLoad(txn,tps,gap);
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }
  public void generateLoad(int N,int tps,int gap) throws Exception{
      
        _resultArea.setText("");

        int tpm=tps/gap; // we are dividing 1000 ms (1 SEC) to 10 slots
        CountDownLatch doneSignal=new CountDownLatch(N);
        StopWatch watch=new StopWatch("TPS");
        CardDetails cardDetails[] = LoadData.getCardDetails();
        LoadResponseListener listener=new LoadResponseListener(doneSignal,N);
        _resultArea.append("started");
        _resultArea.append("\n");  
        watch.start();
        TestCards tc=new TestCards();
        //MUX mux=QMUX.getMUX("nfs");
        MUX mux=QMUX.getMUX("load-mux");
        //Login first
        int noc = 0;
      System.out.println("MUX:"+mux.isConnected()); 

      String[] card={
          "4545458800000000166",
          /*"4545458800000000174",
          "4545458800000000182",
          "4545458800000000190",
          "4545458800000000208",
          "4545458800000000216",
          "4545458800000000224",
          "4545458800000000232",
          "4545458800000000240",
          "4545458800000000257",
          "4545458800000000265"*/
      };
        
      String[] pins={
          "E00C2E597FF082FD",
/*          "CFA0D6ADEA264B89",
          "2BE1B967BD38D5D6",
          "CA538F92643215B3",
          "4F4B9BE7EDDFC7E8",
          "6124F6B6918036AB",
          "CA7FAF23A09833B6",
          "A82EC87BEAB38802",
          "D5E33B5EAAF5F3ED",
          "0F69E63EC5965DF5",
          "B6CE4EF8846BB27D"*/
      };
      
      String[] tracks={
          "4545458800000000166=17099990000000000000",
          /*"4545458800000000208=17099990000000000000",
          "4545458800000000182=17099990000000000000",
          "4545458800000000190=17099990000000000000",
          "4545458800000000208=17099990000000000000",
          "4545458800000000216=17099990000000000000",
          "4545458800000000224=17099990000000000000",
          "4545458800000000232=17099990000000000000",
          "4545458800000000240=17099990000000000000",
          "4545458800000000257=17099990000000000000",
          "4545458800000000265=17099990000000000000"*/
      };
           
      for(int x=0;x<N;x++){
          ISOMsg m=null;
          System.out.println(">>>>>>>>>>>*****Olive Load Test>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+x);
          m=tc.getTestMsg(x,cardDetails[x%noc]);
          //m.dump(System.out, " ");
          //mux.request(m, 30000,listener, m);
          isoSource.send(m);
          //m.dump(System.out, " ");
            if(x%tpm==0)
              ISOUtil.sleep(100);
        }
            
        double sec=watch.getElapsedTime()/1000.0;
        _resultArea.append("Elapsed Time::"+watch.getElapsedTime()/1000.0);
        _resultArea.append("\n");  
        
        _resultArea.append("SEND TPS="+N/sec);
        _resultArea.append("\n");  
     
        _resultArea.append("Sent All Requests::");
        _resultArea.append("\n");  
        //For Receive
        doneSignal.await();
        watch.stop();
         sec=watch.getElapsedTime()/1000.0;
        
        _resultArea.append("Elapsed Time::"+watch.getElapsedTime()/1000.0);
        _resultArea.append("\n");  
        
        _resultArea.append("TPS="+N/sec);
        _resultArea.append("\n");  
        
  }
}

