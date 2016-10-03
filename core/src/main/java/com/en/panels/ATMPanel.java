package com.en.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.jpos.core.Configuration;
import org.jpos.core.SimpleConfiguration;
import org.jpos.iso.MUX;
import org.jpos.iso.packager.DummyPackager;
import org.jpos.q2.iso.ChannelAdaptor;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;

import com.en.listener.AtmChannel;
import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;
import com.en.loaddata.CardDetails;
import com.en.pinhsm.PinUtil;


public class ATMPanel extends JSplitPane implements ActionListener {
	JLabel menuLabel;
	CardDetails[] panelCardDetails = null; 
	JButton button = new JButton("Send");
	public JTextArea logArea;
	JTree f_tree;
	JLabel selectFileLable = new JLabel("No File Selected");
	
	JLabel pinArea = new JLabel("Enter Pin : ");
	JTextField jtxtFld = new JTextField();

	JLabel pinArea2 = new JLabel("Enter New Pin : ");
	JTextField jtxtFld2 = new JTextField();
	
	boolean pinchange = false;
	boolean unsolicited = false;
	
    private final int hGap = 5;
    private final int vGap = 5;
    
	private MUX mux = null;
	private MUX atmmux = null;
	private ChannelAdaptor channel = null;
	JPanel innerControl = null;
	
	public ATMPanel(String label){
		super(JSplitPane.HORIZONTAL_SPLIT);
		menuLabel = new JLabel(label, JLabel.CENTER);
		menuLabel.setForeground(Color.BLACK);
		menuLabel.setFont(new Font("Courier", Font.BOLD, 24));
	}
	
	public void setLogArea (JTextArea logArea) {
		this.logArea = logArea;
	}
	
	public void createPanel(String path) {
        JPanel control=new JPanel();
        innerControl=new JPanel();
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(path); 
        f_tree=new JTree(top);  
        JScrollPane treeView = new JScrollPane(f_tree); 
        f_tree.addTreeSelectionListener(new MyTreeSelectListener());

        control.setLayout(new BorderLayout());
        control.add(menuLabel, BorderLayout.NORTH);
        innerControl.setLayout(new GridLayout(2,2));
        innerControl.add(selectFileLable);
        innerControl.add(new JLabel(""));
        innerControl.add(pinArea);
        innerControl.add(jtxtFld);
        
        control.add(innerControl, BorderLayout.CENTER);
        //control.add(selectFileLable, BorderLayout.CENTER);
        control.add(getButtonPanel(), BorderLayout.SOUTH);

        this.setLeftComponent(treeView);
        this.setRightComponent(control);
        
        this.setSize(750, 450);
        this.setDividerLocation(300);
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
	
	public void actionPerformed(ActionEvent event) {
		try { 
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) f_tree.getLastSelectedPathComponent();
            if (node != null) {
                File t_file=new File(node.toString());
                selectFileLable.setText(node.toString());
                if(t_file.isDirectory()) {  
                	this.logArea.append("\nPlease select proper file");
                } else {
                	TreeNode pNode = node.getParent();
                	this.logArea.append("\nSending Request for :" + pNode.toString() + "/" + node.toString());
                	File atm_file = new File(pNode.toString() + "/" + node.toString());
                	sendNdcRequest2(atm_file.getAbsolutePath());
                }
            }
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendNdcRequest(String fileName) {
		try {
			Socket socket = new Socket("10.0.0.148", 9876);
			OutputStream outputstream = socket.getOutputStream();

			FileInputStream fileInputstream = new FileInputStream(fileName);
			StringBuffer stringbuffer = new StringBuffer();
			int i;
			while ((i = fileInputstream.read()) != -1)
				stringbuffer.append((char) i);
			char[] ac = stringbuffer.toString().toCharArray();
			for (int j = 0; j < ac.length; j++)
				outputstream.write(ac[j]);
			readResponse(socket);
			socket.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void sendNdcRequest1(String fileName) {
		try {

			FileInputStream fileInputstream = new FileInputStream(fileName);
			StringBuffer stringbuffer = new StringBuffer();
			int i;
			while ((i = fileInputstream.read()) != -1)
				stringbuffer.append((char) i);
			
			AtmPackager atmPack = new AtmPackager("file:./cfg/atm/ndc-");
			AtmNdc ndcMsgReq= new AtmNdc(atmPack);
			//ndcMsgReq.unpack(stringbuffer.toString().getBytes());
			//atmmux=QMUX.getMUX("atm-mux");
			AtmChannel atmChannel = new AtmChannel("10.0.0.148",9876, new DummyPackager());
			SimpleConfiguration simconf = new SimpleConfiguration();
			simconf.put("schema", "file:./cfg/atm/ndc-");
			atmChannel.setConfiguration(simconf);
			atmChannel.setTimeout(40000);
			atmChannel.connect();
			if(!unsolicited) {
				ndcMsgReq.unpack(stringbuffer.toString().getBytes());
				System.out.println(stringbuffer.toString());
				atmChannel.send(stringbuffer.toString().getBytes());
				AtmNdc ndcMsgRes = (AtmNdc)atmChannel.receive();
			
				AtmPackager atmpackRes = ndcMsgRes.getFSDMsg();
			
				System.out.println("Next State Number is = " + atmpackRes.get("next-state-id", "000"));
				this.logArea.append("\nNext State Number is = " + atmpackRes.get("next-state-id", " Default : 000"));
			} else {
				atmChannel.send(stringbuffer.toString().getBytes());
			}
			
/*			if(atmmux.isConnected()) {
				AtmNdc ndcMsgRes = (AtmNdc)atmmux.request(ndcMsgReq, 40000);
			} else {
				System.out.println("Mux is not connected");
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			
		}
	}
	
   public void readResponse(Socket socket) throws IOException {
	      socket.setSoTimeout(30000);
	      InputStream inputstream = socket.getInputStream();
	      if(inputstream != null) {
		      DataInputStream datainputstream = new DataInputStream(inputstream);
		      while (true) {
		         StringBuffer stringbuffer1 = new StringBuffer();
		         if (datainputstream.available() > 0) {
		            while (datainputstream.available() > 0) {
		               stringbuffer1.append((char)inputstream.read());
		            }
		            String strMessage = stringbuffer1.toString();
		            this.logArea.append("\nResponce Message >>" + strMessage);
		            break;
		         }
		      }
   		  }
	      return;
   }
	   
	public class MyTreeSelectListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e)    
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) f_tree.getLastSelectedPathComponent();    
            if (node != null) {
                File t_file=new File(node.toString());
                selectFileLable.setText(node.toString());

                if(node.toString() != null && node.toString().contains("POWERUP")) {
                    System.out.println("It is POWERUP Message");
                    innerControl.setLayout(new GridLayout(1,1));
                    innerControl.remove(pinArea);
                    innerControl.remove(jtxtFld);
                    unsolicited = true;
                } else {
                	unsolicited = false;
                    if(node.toString() != null && node.toString().contains("PIN CHANGE")) {
                    	System.out.println("It is PIN CHANGE");
                    	innerControl.setLayout(new GridLayout(3,2));
                        innerControl.add(pinArea2);
                        innerControl.add(jtxtFld2);
                        pinchange = true;
                    } else {
                    	innerControl.setLayout(new GridLayout(2,2));
                    	innerControl.remove(pinArea2);
                    	innerControl.remove(jtxtFld2);
                    	pinchange = false;
                    }
                }

                if(t_file.isDirectory()) {    
                    try {    
                        for(File f : t_file.listFiles()) 
                        {    
                            if(f.isDirectory()) {
                            	node.add(new DefaultMutableTreeNode(f.getPath() + File.separatorChar, true));  
                            } else {    
                            	node.add(new DefaultMutableTreeNode(f.getName(), false));     
                            }    
                        }       
                    }    
                    catch(Exception ge)    
                    {   
                    	ge.printStackTrace();
                    }                   
                }    
            }    
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
			AtmNdc ndcMsgReq= new AtmNdc(atmPack);
			
			atmmux=QMUX.getMUX("atm-mux");
			long timeout= 30000;
			
			if(!unsolicited) { 
				ndcMsgReq.unpack(stringbuffer.toString().getBytes());
				
    			System.out.println(jtxtFld.getText());
    			PinUtil pinUtil = new PinUtil();
    			String track2Data = atmPack.get("track2");
    			track2Data = pinUtil.getTrack2DataWithoutSentimental(track2Data); 
    			String cardNo = track2Data.substring(0, track2Data.indexOf("=")); 
        			
        	        if(jtxtFld.getText() != null && jtxtFld.getText().length()>0) {
        	        	String pinblock = pinUtil.createAtmPinBlock(cardNo, jtxtFld.getText());
        	        	System.out.println("Pinblock : " + pinblock);
        	        	atmPack.set("pin-buffer-A", pinblock);
        	        }
        	        
        	        if(pinchange && jtxtFld2.getText() != null && jtxtFld2.getText().length()>0) {
        	        	String pinblock = pinUtil.createAtmPinBlock(cardNo, jtxtFld2.getText());
        	        	System.out.println("Pinblock New: " + pinblock);
        	        	atmPack.set("last-status-data", "U" + pinblock);
        	        }
    			AtmNdc ndcMsgRes = (AtmNdc)atmmux.request(ndcMsgReq, timeout);
    			
				if (ndcMsgRes != null) {
					AtmPackager atmpackRes = ndcMsgRes.getFSDMsg();

					System.out.println("Printer Data = " + atmpackRes.get("printer-data", "000"));
					this.logArea.append("\nPrinter Data = " + atmpackRes.get("printer-data", " Default : 000"));
				}
    			
			} else {
	            this.logArea.append("\n Send powerup Message");
				AtmPackager atmPack1 = new AtmPackager("file:./cfg/atm/ndc-", "12-B");
				AtmNdc ndcMsgReq1= new AtmNdc(atmPack1);
				ndcMsgReq1.unpack(stringbuffer.toString().getBytes());
				timeout = 1000;
				atmmux.request(ndcMsgReq1, timeout);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			
		}
	}
}
