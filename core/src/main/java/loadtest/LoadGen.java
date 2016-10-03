package loadtest;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.jpos.q2.Q2;
import org.jpos.util.NameRegistrar;

import com.en.filewatcher.FileWatcher;
import com.en.loaddata.LoadData;
import com.en.menu.MainMenus;
import com.en.panels.ATMLoadTest;
import com.en.panels.ATMPanel;
import com.en.panels.CardsPanel;

public class LoadGen {
	public static void main(String[] args) throws Exception {
    Q2.main(args);
    LoadData ld = new LoadData();
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
        	TestToolUI ttu = new TestToolUI("Shetab Simulator");
        	
        	String[] cardMenuItems = new String[] {"Debit Card", "Gift Card", "BON Card", "Credit Card"};
        	String[] cardFiles = new String[] {"./cfg/LoadData_Debit.xml", "./cfg/LoadData_GIFT.xml", "./cfg/LoadData_BON.xml", "./cfg/LoadData_Credit.xml"};
        	MainMenus mainMenus = new MainMenus(ttu, cardMenuItems);
        	
        	JTextArea logArea = new JTextArea(13, 75);
        	logArea.setLineWrap(true);
        	
			NameRegistrar.register("txtarea", logArea);
			
			JPanel logPanel = new JPanel();
			logPanel.add(logArea);
			
        	for(int i=0; i<cardMenuItems.length; i++) {
            	CardsPanel cardPanel = new CardsPanel(cardMenuItems[i]);
            	cardPanel.setLogArea(logArea);
            	cardPanel.createPanel();
            	
            	FileWatcher.StartWatcher(cardFiles[i], cardPanel);
            	if(i == 0 ) {
            		ttu.setMidPanel(cardPanel);
            		ttu.repaint();
            	}
                mainMenus.setCardsPanel(cardMenuItems[i], cardPanel);

        	}
        	
        	ATMPanel atmPanel = new ATMPanel("ATM Txns");
        	atmPanel.setLogArea(logArea);
        	atmPanel.createPanel("./atmmessages");
        	mainMenus.setCardsPanel("ATM Txns", atmPanel);
           
        	ATMLoadTest atmLoad = new ATMLoadTest("ATM Load");
        	atmLoad.setLogArea(logArea);
        	atmLoad.createPanel("");
        	mainMenus.setCardsPanel("ATM Load", atmLoad);
        	
        	ttu.setlogPanel(logPanel);
            ttu.setJMenuBar(mainMenus);
            ttu.setSize(900, 500);
            ttu.setLocation(200,100);
            ttu.setVisible(true);
        }
    });
  }
}
