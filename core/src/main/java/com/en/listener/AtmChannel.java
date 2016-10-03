package com.en.listener;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

public class AtmChannel extends BaseChannel {
    String schema;
    public AtmChannel () {
        super();
    }
    
    BufferedReader reader = null;
   
    public AtmChannel (String host, int port, ISOPackager p) {
        super(host, port, p);
    }
   
    public AtmChannel (ISOPackager p) throws IOException {
        super(p);
    }
  
    public AtmChannel (ISOPackager p, ServerSocket serverSocket) 
        throws IOException
    {
        super(p, serverSocket);
    }
    
    public ISOMsg createMsg() {
        return new AtmNdc (new AtmPackager(schema));
    }
    public void setConfiguration (Configuration cfg)
        throws ConfigurationException 
    {
        super.setConfiguration (cfg);
        schema = cfg.get ("schema");
    }
   protected int getMessageLength() throws IOException, ISOException {
        int len = super.getMessageLength();
        LogEvent evt = new LogEvent (this, "fsd-channel-debug");
        evt.addMessage ("received message length: " + len);
        Logger.log (evt);
        return len;
    }

   protected byte[] streamReceive() throws IOException {
       int i=0;
       byte[] buf = new byte[4096];
       for (i=0; i<4096; i++) {
           int c = -1;
           try {
               c = serverIn.read();
           } catch (SocketException e) { 
        	   e.printStackTrace();
           }catch (Exception e) {
        	   e.printStackTrace();
           }
           if (c == 03){
               break;
           } 
           else if (c == -1){
               throw new EOFException("connection closed");
           }
           buf[i] = (byte) c;
       }
       if (i == 4096){
           throw new IOException("packet too long");
       }

       byte[] d = new byte[i];
       System.arraycopy(buf, 0, d, 0, i);
       writeToFile(d);
       return d;
   }
   
   private void writeToFile(byte[] b) {
	   FileOutputStream fos = null;
   
	   try {
		   // fos = new FileOutputStream(new File("atmmessages/response/response.txt"));
		   fos = new FileOutputStream(new File("response.txt"));
		   fos.write(b);
	   } catch (Exception e) {
		   e.printStackTrace();
	   } finally {
		   if(fos != null) {
			   try {
				   fos.close();
			   } catch (IOException ioe) {
				   ioe.printStackTrace();
			   }
		   }
	   }
	   
   }
}

