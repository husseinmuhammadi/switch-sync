package com.en.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import loadtest.LoadResponseListener;
import loadtest.TestCards;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.MUX;
import org.jpos.q2.iso.ChannelAdaptor;
import org.jpos.q2.iso.QMUX;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceUtil;
import org.jpos.util.NameRegistrar;

import com.en.filewatcher.IMyWatcher;
import com.en.loaddata.CardDetails;


public class CardsPanel extends JPanel implements ActionListener,IMyWatcher {
	JLabel menuLabel;
	JComboBox transactionList = null;
	CardDetails[] panelCardDetails = null; 
	JButton button = new JButton("Send");
	public JTextArea logArea;
    
    private final int hGap = 5;
    private final int vGap = 5;
    
	private MUX mux = null;
	private ChannelAdaptor channel = null;
    
	public CardsPanel(String label){
		menuLabel = new JLabel(label, JLabel.CENTER);
		menuLabel.setForeground(Color.BLACK);
		menuLabel.setFont(new Font("Courier", Font.BOLD, 24));
	}
	
	public void setLogArea (JTextArea logArea) {
		this.logArea = logArea;
	}
	
	public void createPanel() {
        JPanel control=new JPanel();
        JPanel innerPanel=new JPanel();
        
        innerPanel.setLayout(new GridLayout(1,2,hGap, vGap));
        
        innerPanel.setBorder(
                BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));
        
        transactionList = new JComboBox();
        innerPanel.add(new JLabel("Select Transaction :"));
        innerPanel.add(transactionList);
        
        control.setLayout(new BorderLayout());
        control.add(menuLabel, BorderLayout.NORTH);
        control.add(innerPanel, BorderLayout.CENTER);
        control.add(getButtonPanel(), BorderLayout.SOUTH);

        this.setSize(750, 450);
        this.add(control);
        
        try {
        	mux=QMUX.getMUX("load-mux");
        	
        	channel = (ChannelAdaptor) NameRegistrar.get("loadtest");
        	System.out.println("MUX:"+channel.isConnected());
        	if(!channel.isConnected()) {
        		logArea.append("\nChannel Not Connected ???");
        	} else {
        		logArea.append("\nChannel is Connected now !!!");
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
	    //inner.add(logArea, BorderLayout.SOUTH);
	    button.addActionListener(this);
	    return inner;
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
	
	public void actionPerformed(ActionEvent event) {
		try { 
			System.out.println("Card Number : " + transactionList.getSelectedIndex());
			int txnNum = transactionList.getSelectedIndex() + 1;

			runPerticularTxt(txnNum);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void runPerticularTxt (int txnNumber) throws Exception{
    	ISOMsg m=null;
	    //CardDetails cardDetails[] = LoadData.getCardDetails();
	    TestCards tc=new TestCards();
	    LoadResponseListener listener=new LoadResponseListener(null,1);

	    if(panelCardDetails.length >= txnNumber) {
	    	logArea.append("\nsending message : " + panelCardDetails[txnNumber-1].getTransactionName());
	    	Space space_ = SpaceFactory.getSpace("STAN");
	    	long stan = SpaceUtil.nextLong(space_, "STAN");
	    	m=tc.getTestMsg(stan, panelCardDetails[txnNumber-1]);
	    	mux=QMUX.getMUX("load-mux");
	    	if (mux != null && mux.isConnected()) {
	    		mux.request(m, 1000,listener, new Object());
	    	} else {
	    		logArea.append("\nSwitch is not connected");
	    	}
	    } else {
	    	System.out.println("Please enter the transaction within the range of Card Details provided.");
	    }

	}
}
