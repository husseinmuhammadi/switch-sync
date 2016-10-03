package com.en.filewatcher;

import java.util.*;
import java.io.*;

import com.en.loaddata.LoadData;

public abstract class FileWatcher extends TimerTask {
  private long timeStamp;
  private File file;
  IMyWatcher jf;
  
  public FileWatcher( File file ) {
    this.file = file;
    this.timeStamp = file.lastModified();
  }

  public FileWatcher( File file, IMyWatcher jf ) {
	    this.file = file;
	    this.timeStamp = file.lastModified();
	    this.jf = jf;
  }
  
  public final void run() {
    long timeStamp = file.lastModified();

    if( this.timeStamp != timeStamp ) {
      this.timeStamp = timeStamp;
      onChange(file, jf);
    }
  }
  protected abstract void onChange( File file);
  
  protected abstract void onChange( File file, IMyWatcher jf );
  
  public static void StartWatcher(String filename, IMyWatcher jf) {
	    // monitor a single file
	  	LoadData.loadXml(new File(filename));
	  	jf.setTransactionCombo(LoadData.getCardDetails());
	  	
	    TimerTask task = new FileWatcher( new File(filename), jf) {
	      protected void onChange( File file, IMyWatcher jf) {
	        // here we code the action on a change
	        System.out.println( "File "+ file.getName() +" have change !" );
	        LoadData.loadXml(file);
	        jf.setTransactionCombo(LoadData.getCardDetails());
	      }
	      
	      protected void onChange( File file) {
		        // here we code the action on a change
		        System.out.println( "File "+ file.getName() +" have change !" );
		        LoadData.loadXml();
		        jf.setTransactionCombo(LoadData.getCardDetails());
		      }
	    };

	    Timer timer = new Timer();
	    // repeat the check every second
	    timer.schedule( task , new Date(), 5000 );
  }
}