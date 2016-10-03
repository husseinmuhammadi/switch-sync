package com.en.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;

import loadtest.ATMLoadResponseListener;
import loadtest.ATMTestMessage;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.MUX;
import org.jpos.q2.iso.ChannelAdaptor;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.perf4j.StopWatch;

import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;
import com.en.loaddata.CardDetails;
import com.junit.util.PropertyLoader;

public class ATMLoadTest extends JPanel implements ActionListener {
	JLabel menuLabel;
	CardDetails[] panelCardDetails = null;
	JButton button = new JButton("Send");
	public JTextArea logArea;
	JTree f_tree;
	JLabel selectFileLable = new JLabel("No File Selected");

	private MUX atmmux = null;
	private ChannelAdaptor channel = null;

	public ATMLoadTest(String label) {
		menuLabel = new JLabel(label, JLabel.CENTER);
		menuLabel.setForeground(Color.BLACK);
		menuLabel.setFont(new Font("Courier", Font.BOLD, 24));
	}

	public void setLogArea(JTextArea logArea) {
		this.logArea = logArea;
	}

	public void createPanel(String path) {
		JPanel control = new JPanel();
		control.setLayout(new BorderLayout());
		control.add(getButtonPanel(), BorderLayout.SOUTH);

		this.setSize(750, 450);
		this.add(control);

		try {
			atmmux = QMUX.getMUX("atm-mux");

			channel = (ChannelAdaptor) NameRegistrar.get("atmtest");
			System.out.println("ATM MUX:" + channel.isConnected());
			if (!channel.isConnected()) {
				logArea.append("\nATM Channel Not Connected ???");
			} else {
				logArea.append("\nATM Channel is Connected now !!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected JComponent getButtonPanel() {
		JPanel inner = new JPanel();
		button = new JButton("Send");
		button.setSize(10, 6);
		inner.setLayout(new BorderLayout());
		inner.add(button, BorderLayout.NORTH);
		button.addActionListener(this);
		return inner;
	}

	public void actionPerformed(ActionEvent event) {
		try {
			this.logArea.setText("");
			
			int tps = Integer.parseInt(PropertyLoader.getInstance().getProperty("TPS"));
			int gap = Integer.parseInt(PropertyLoader.getInstance().getProperty("GAP"));
			int N = Integer.parseInt(PropertyLoader.getInstance().getProperty("TOTAL_TXN"));
			long timout = Long.parseLong(PropertyLoader.getInstance().getProperty("ATM_TIMEOUT"));
		    int tpm=tps/gap; // we are dividing 1000 ms (1 SEC) to 10 slots
		    int totalWaitMillis = 1000/gap;
		    
		    CountDownLatch doneSignal=new CountDownLatch(N);
	    	Object handback = new Object();
		    StopWatch watch=new StopWatch("TPS");
		    ATMLoadResponseListener listener=new ATMLoadResponseListener(doneSignal,N);

			ATMTestMessage atest = new ATMTestMessage();
			
		    this.logArea.append("started");
		    this.logArea.append("\n");  
		    watch.start();

			for (int x = 0; x < N; x++) {
				ISOMsg m = null;
				//System.out.println("Transaction Number = " + x);
				m = atest.getTestMsg(x);
				atmmux.request(m, timout, listener, handback);
				if(x%tpm==0)
			          ISOUtil.sleep(totalWaitMillis);
			}

			double sec = watch.getElapsedTime() / 1000.0;
			this.logArea.append("Elapsed Time::" + sec);
			this.logArea.append("\n");

			this.logArea.append("SEND TPS=" + N / sec);
			this.logArea.append("\n");

			this.logArea.append("Sent All Requests::");
			this.logArea.append("\n");
			// For Receive
			doneSignal.await();
			watch.stop();
			sec=watch.getElapsedTime()/1000.0;

			this.logArea.append("Elapsed Time::" + sec);
			this.logArea.append("\n");

			this.logArea.append("TPS=" + N / sec);
			this.logArea.append("\n");

			this.logArea.append("Success Count =" + listener.getTotalSuccess());
			this.logArea.append("\n");

			this.logArea.append("Failure Count =" + listener.getTotalFailure());
			this.logArea.append("\n");
			
			this.logArea.append("Expired Count =" + listener.getTotalExpired());
			this.logArea.append("\n");
			
			listener.setTotalSuccess(0);
			listener.setTotalFailure(0);
			listener.setTotalExpired(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendNdcRequest2(String fileName) {
		try {
			FileInputStream fileInputstream = new FileInputStream(fileName);
			StringBuffer stringbuffer = new StringBuffer();
			int i;
			while ((i = fileInputstream.read()) != -1)
				stringbuffer.append((char) i);

			AtmPackager atmPack = new AtmPackager("file:./cfg/atm/ndc-", "request");
			AtmNdc ndcMsgReq = new AtmNdc(atmPack);

			atmmux = QMUX.getMUX("atm-mux");

			ndcMsgReq.unpack(stringbuffer.toString().getBytes());

			long timeout = 30000;
			AtmNdc ndcMsgRes = (AtmNdc) atmmux.request(ndcMsgReq, timeout);

			AtmPackager atmpackRes = ndcMsgRes.getFSDMsg();

			System.out.println("Printer Data = " + atmpackRes.get("printer-data", "000"));
			this.logArea.append("\nPrinter Data = " + atmpackRes.get("printer-data", " Default : 000"));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}
	}
}
