package loadtest;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.MUX;
import org.jpos.q2.iso.ChannelAdaptor;
import org.jpos.q2.iso.QMUX;
//import org.jpos.q2.iso.QServer;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceUtil;
import org.jpos.util.NameRegistrar;
import org.perf4j.StopWatch;

//import com.en.base1.VAPChannel;
import com.en.filewatcher.IMyWatcher;
import com.en.loaddata.CardDetails;
import com.en.loaddata.LoadData;

public class LoadTestUI extends JFrame implements ActionListener, IMyWatcher {

	private static final long serialVersionUID = -4201803498634433156L;
	private static final String TXN_LBL_TXT = "Volume";
	private static final String TIME_LBL_TXT = "Time";
	private static final String ANY_ONE_LBL_TXT = "Single";
	private static int STOP_TIMER = 1;
	private int time_or_txn = 3;
	private MUX mux = null;
	private ChannelAdaptor channel = null;
	JTextArea _resultArea = new JTextArea(6, 20);
    JButton button = new JButton("LoadTest");
    JTextField txtNo = new JTextField("60");
    JTextField txtPerNo = new JTextField("1");
    JTextField txtTPS = new JTextField("30"); 
    JTextField txtGAP = new JTextField("10");
    JTextField txtHours = new JTextField("HHH");
    JTextField txtMins = new JTextField("MM");
    JLabel muxConnection = new JLabel();
    
    JComboBox transactionList = null;
	CardDetails[] panelCardDetails = null; 
    //ButtonGroup group = new ButtonGroup();
    //JRadioButton txnButton = new JRadioButton(TXN_LBL_TXT);    
    //JRadioButton timeButton = new JRadioButton(TIME_LBL_TXT);
    //JRadioButton pertButton = new JRadioButton(ANY_ONE_LBL_TXT);
    
    public LoadTestUI(String title) {
    	super(title);
        JScrollPane scrollingArea = new JScrollPane(_resultArea);
        JPanel content = new JPanel();

        String[] petStrings = {"Single", "Volume", "Time"};
        JComboBox petList = new JComboBox(petStrings);
        petList.setSelectedIndex(0);
        petList.addActionListener(this);
        
        setTransactionCombo(LoadData.getCardDetails());
 
        //transactionList.addActionListener(this);
        
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout());
        timePanel.add(new JLabel("Hours"));
        timePanel.add(txtHours);
        timePanel.add(new JLabel("Mins"));
        timePanel.add(txtMins);
		txtPerNo.enable();
		txtNo.disable();
		txtHours.disable();
		txtMins.disable();  
		txtTPS.disable();
        txtGAP.disable();
        
        txtPerNo.setBackground(Color.white);
        txtTPS.setBackground(Color.gray);
        txtGAP.setBackground(Color.gray);
		txtHours.setBackground(Color.gray);
		txtMins.setBackground(Color.gray);
		txtNo.setBackground(Color.gray);
		
        content.setLayout(new BorderLayout());
        button.addActionListener(this);
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(1,1));
        
        //JPanel buttonControl=new JPanel();
       // buttonControl.setLayout(new GridLayout(1,3));
        
        
        JPanel control=new JPanel();
        control.setLayout(new GridLayout(8,2));
        
        //txnButton.addActionListener(this);
        //txnButton.setSelected(true);
        //timeButton.addActionListener(this);
        //pertButton.addActionListener(this);
        //group.add(txnButton);
        //group.add(timeButton);
        //group.add(pertButton);
        
        //buttonControl.add(txnButton);
        //buttonControl.add(timeButton);
        //buttonControl.add(pertButton);
        
        control.add(new JLabel("Test Type "));
        control.add(petList);
        control.add(new JLabel("Transaction Count:"));
        control.add(txtNo);
        control.add(new JLabel("Transaction No:"));
        //control.add(txtPerNo);
        control.add(transactionList);
        		
        control.add(new JLabel("Time"));
        control.add(timePanel);
        control.add(new JLabel("TPS:"));
        control.add(txtTPS);
        control.add(new JLabel("GAP:"));
        control.add(txtGAP);
        
        //button.setBounds(80, 80, 80, 80);
        //mainPanel.add(button);
        //mainPanel.add(buttonControl);
        
        try {
        	mux=QMUX.getMUX("load-mux");
        	channel = (ChannelAdaptor) NameRegistrar.get("loadtest");
        	System.out.println("MUX:"+channel.isConnected());
        	if(!channel.isConnected()) {
        		muxConnection.setText("Channel Not Connected");
        		muxConnection.setForeground(Color.RED);
        	} else {
        		muxConnection.setText("Channel Connected");
        		muxConnection.setForeground(Color.BLUE);
        	}
        } catch (Exception e) {
        	System.out.println("Problem in connecting server");
    		muxConnection.setText("Problem In connecting Server");
    		muxConnection.setBackground(Color.blue);
        }
        control.add(new JLabel("Status:"));
        control.add(muxConnection);
        //content.add(button,BorderLayout.NORTH);
        control.add(button);
        mainPanel.add(control);
        content.add(mainPanel, BorderLayout.NORTH);
        content.add(scrollingArea, BorderLayout.SOUTH);

        this.setContentPane(mainPanel);
        this.setTitle(title);
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
			String petName = "LoadTest";
			if(event.getSource() instanceof JComboBox) {
				JComboBox cb = (JComboBox)event.getSource();
	        	petName = (String)cb.getSelectedItem();
			}
			
			System.out.println("Card Number : " + transactionList.getSelectedIndex());
			
			System.out.println("test: " + petName);
	        
			if(TXN_LBL_TXT.equalsIgnoreCase(petName)) {
				time_or_txn = 1;
				txtPerNo.disable();
				txtNo.enable();
				txtHours.disable();
				txtMins.disable();
				txtTPS.enable();
		        txtGAP.enable();
		        
		        txtNo.setBackground(Color.white);
				txtHours.setBackground(Color.gray);
				txtMins.setBackground(Color.gray);
				txtPerNo.setBackground(Color.gray);
				txtTPS.setBackground(Color.white);
		        txtGAP.setBackground(Color.white);
			} else if(TIME_LBL_TXT.equalsIgnoreCase(petName)){
				time_or_txn = 2;
				txtPerNo.disable();
				txtNo.disable();
				txtHours.enable();
				txtMins.enable();
				txtTPS.enable();
		        txtGAP.enable();
		        
				txtPerNo.setBackground(Color.gray);
				txtHours.setBackground(Color.white);
				txtMins.setBackground(Color.white);
				txtNo.setBackground(Color.gray);
				txtTPS.setBackground(Color.white);
		        txtGAP.setBackground(Color.white);
			} else if(ANY_ONE_LBL_TXT.equalsIgnoreCase(petName)){
				time_or_txn = 3;
				txtPerNo.enable();
				txtNo.disable();
				txtHours.disable();
				txtMins.disable();  
				txtTPS.disable();
		        txtGAP.disable();
		        
		        txtPerNo.setBackground(Color.white);
				
		        txtTPS.setBackground(Color.gray);
		        txtGAP.setBackground(Color.gray);
				txtHours.setBackground(Color.gray);
				txtMins.setBackground(Color.gray);
				txtNo.setBackground(Color.gray);
			} else {

/*				Map m = NameRegistrar.getMap();
				Set s = m.keySet();
				Iterator i = s.iterator();
				while(i.hasNext()) {
					String ms = (String) i.next();
					System.out.println("MSD :: " + ms);
					QServer qs = (QServer)NameRegistrar.get("xml-server");
					VAPChannel vc = (VAPChannel)NameRegistrar.get(qs.getChannel());
					LoadListener ll = vc.get
				}
*/				
//				System.out.println("Txn Time : " + time_or_txn);
				int tps=Integer.parseInt(txtTPS.getText());
				int gap=Integer.parseInt(txtGAP.getText());
				int noOfTxn = 0;
				if(time_or_txn == 2) {
					String strHours = txtHours.getText();
					String strMins = txtMins.getText();
					int hrs = 0;
					int mins = 0;
					if(strHours != null &&  !strHours.equalsIgnoreCase("HHH")) {
						hrs=Integer.parseInt(strHours);
					}
					
					if(strMins != null &&  !strMins.equalsIgnoreCase("MM")) {
						mins=Integer.parseInt(strMins);
					}

					noOfTxn = (hrs*60*60 + mins*60)*tps;
					long millis = hrs*60*60*1000 + mins*60*1000;
					
					if(millis != 0) {
						MyTimer mytimer = new MyTimer();
						mytimer.setTimeInMillis(millis);
						Thread t = new Thread(mytimer);
						t.start();
						generateTimeLoad(noOfTxn, tps, gap);
					} else {
						_resultArea.setText("Please specify Hours and Mins");
					}
				} else if(time_or_txn == 3) {
				
					//String txnNo = txtPerNo.getText();
					int txnNum = transactionList.getSelectedIndex() + 1;
/*					if(txnNo != null &&  !txnNo.equalsIgnoreCase("")) {
						txnNum=Integer.parseInt(txnNo);
					}*/
					if(txnNum != 0) {
						runPerticularTxt(txnNum);
					} else {
						_resultArea.setText("Please Transaction number");
					}
				} else {
					int txn=Integer.parseInt(txtNo.getText());
					generateLoad(txn,tps,gap);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void generateLoad(int N,int tps,int gap) throws Exception {
		  
	    _resultArea.setText("");

	    int tpm=tps/gap; // we are dividing 1000 ms (1 SEC) to 10 slots
	    CountDownLatch doneSignal=new CountDownLatch(N);
    	Object handback = new Object();
	    StopWatch watch=new StopWatch("TPS");
	    CardDetails cardDetails[] = LoadData.getCardDetails();
	    LoadResponseListener listener=new LoadResponseListener(doneSignal,N);

	    _resultArea.append("started");
	    _resultArea.append("\n");  
	    watch.start();
	    TestCards tc=new TestCards();

		int noc = 0;
		if(cardDetails != null) {
			noc = cardDetails.length;
		}
		
		for(int x=0;x<N;x++){
	    	ISOMsg m=null;
	    	System.out.println(">>>>>>>>>>>*****Datavsn Load Test>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+x);
	    	m=tc.getTestMsg(x,cardDetails[x%noc]);
	    	//m.dump(System.out, " ");
		    mux.request(m, 1000,listener, new Object());
		    //m.dump(System.out, " ");
	      //Following condtion commented by Bhalchandra on24-Apr-2014
	       /* if(x%tpm==0)
	          ISOUtil.sleep(100);*/
	    }
            
		    double sec=watch.getElapsedTime()/1000.0;
		    _resultArea.append("Elapsed Time::"+watch.getElapsedTime()/1000.0);
		    _resultArea.append("\n");  
		    
		    _resultArea.append("SEND TPS="+N/sec);
		    _resultArea.append("\n");  
	   
		    _resultArea.append("Sent All Requests::");
		    _resultArea.append("\n");  
		    //For Receive
		    //doneSignal.await();
		    //watch.stop();
		    // sec=watch.getElapsedTime()/1000.0;
		    
		    _resultArea.append("Elapsed Time::"+watch.getElapsedTime()/1000.0);
		    _resultArea.append("\n");  
		    
		    _resultArea.append("TPS="+N/sec);
		    _resultArea.append("\n");  
		    
		    _resultArea.append("Success Count ="+ listener.getTotalSuccess());
		    _resultArea.append("\n");  

		    _resultArea.append("Failure Count ="+ listener.getTotalFailure());
		    _resultArea.append("\n");  
	}
	
	public void runPerticularTxt (int txnNumber) throws Exception{
		_resultArea.setText("");
    	ISOMsg m=null;
	    //CardDetails cardDetails[] = LoadData.getCardDetails();
	    TestCards tc=new TestCards();
	    LoadResponseListener listener=new LoadResponseListener(null,1);

	    if(panelCardDetails.length >= txnNumber) {
	    	System.out.println("sending message : " + txnNumber);
	    	Space space_ = SpaceFactory.getSpace("STAN");
	    	long stan = SpaceUtil.nextLong(space_, "STAN");
	    	m=tc.getTestMsg(stan, panelCardDetails[txnNumber-1]);
    	
	    	mux.request(m,1000);
	    } else {
	    	System.out.println("Please enter the transaction within the range of Card Details provided.");
	    	_resultArea.append("Elapsed Time::");
	    }

	}
	
	public void generateTimeLoad(int noOfTxn, int tps,int gap) throws Exception {
	    _resultArea.setText("");

	    int tpm=tps/gap; // we are dividing 1000 ms (1 SEC) to 10 slots
	    CountDownLatch doneSignal=new CountDownLatch(noOfTxn);
    	Object handback = new Object();
	    StopWatch watch=new StopWatch("TPS");
	    CardDetails cardDetails[] = LoadData.getCardDetails();
	    LoadResponseListener listener=new LoadResponseListener(doneSignal,noOfTxn);

	    _resultArea.append("started");
	    _resultArea.append("\n");  
	    watch.start();
	    TestCards tc=new TestCards();

		int noc = 0;
		if(cardDetails != null) {
			noc = cardDetails.length;
		}

/*		
		while (STOP_TIMER == 1) {
			System.out.println("Stop Timer");
		}
*/		
		int x = 1;
		long counter = 0;
		while (STOP_TIMER == 1) {
	    	ISOMsg m=null;
	    	System.out.println("sending message : " + x);
	    	m=tc.getTestMsg(x,cardDetails[x%noc]);

	    	mux.request(m,120000,listener, new Object());

	    	if(x%tpm==0)
	          ISOUtil.sleep(100);
	        
	    	if(x <= 999999) {
	    		x = x + 1;
	    	} else {
	    		x = 1;
	    	}
	        
	        counter = counter + 1;
	    }

		double sec=watch.getElapsedTime()/1000.0;
		_resultArea.append("Elapsed Time::"+watch.getElapsedTime()/1000.0);
		_resultArea.append("\n");  
		    
		_resultArea.append("SEND TPS="+counter/sec);
		_resultArea.append("\n");  
	   
		_resultArea.append("Sent All Requests::");
		_resultArea.append("\n");  
		    //For Receive
		doneSignal.await(30, TimeUnit.SECONDS);
		watch.stop();
		sec=watch.getElapsedTime()/1000.0;
		    
		_resultArea.append("Elapsed Time::"+watch.getElapsedTime()/1000.0);
		_resultArea.append("\n");  
		
		_resultArea.append("TPS=" + counter/sec);
		_resultArea.append("\n");  
		    
		_resultArea.append("Success Count ="+ listener.getTotalSuccess());
		_resultArea.append("\n");  

		_resultArea.append("Failure Count ="+ listener.getTotalFailure());
		_resultArea.append("\n");  		
	}
	
	public void setTransactionCombo(CardDetails cardDetails[]){
		panelCardDetails = cardDetails;
		String transactions[] = {"None"};
		if(cardDetails.length > 0) {
			transactions = new String[cardDetails.length];
			for (int i=0; i<cardDetails.length; i++) {
				transactions[i] = cardDetails[i].getTransactionName();
			}
		} 
		if (transactionList != null) {
			transactionList.removeAllItems();
			for (int i=0; i<transactions.length; i++) {
				transactionList.addItem(transactions[i]);
			} 
			transactionList.repaint();
		} else {
			transactionList = new JComboBox(transactions);
			transactionList.repaint();
		}
	    transactionList.setSelectedIndex(0);
	}
	
	private class MyTimer implements Runnable {
		long timeinmillis = 0;
		
		public void setTimeInMillis(long timeinmil) {
			timeinmillis = timeinmil;
		}
		
		public void run() {
			ISOUtil.sleep(timeinmillis);
			STOP_TIMER = 2;
		}
	}
}

