package com.en.menu;

import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import com.en.panels.CardsPanel;
import loadtest.TestToolUI;

public class MainMenus extends JMenuBar implements ActionListener{

   TestToolUI ttu;
   private HashMap<String, JComponent> mapcardsPanel;

   	public JComponent getCardsPanel(String panelName) {
   		return mapcardsPanel.get(panelName);
   	}

	public void setCardsPanel(String panelName, JComponent cardsPanel) {
		if(mapcardsPanel == null) {
			mapcardsPanel = new HashMap<String, JComponent>();
		}
		this.mapcardsPanel.put(panelName, cardsPanel);
	}
	
	String[] testMenuItems;
	String[] loadMenuItems = new String[] {"Single", "Volume", "Time"};
	String[] atmMenuItems = new String[] {"ATM Txns", "ATM Load"};

   
   public MainMenus() {
   }

   public MainMenus(TestToolUI ttu, String[] testMenuItems)
   {
	  this.testMenuItems = testMenuItems;
	  this.ttu = ttu;
      JMenu testMenu = new JMenu("TestCase");
      JMenu loadMenu = new JMenu("Load");
      JMenu atmMenu = new JMenu("ATM");

      
      for (int i=0; i < testMenuItems.length; i++) {
         JMenuItem item = new JMenuItem(testMenuItems[i]);
         item.addActionListener(this);
         testMenu.add(item);
      }

      // Assemble the File menus with keyboard accelerators.
      for (int i=0; i < loadMenuItems.length; i++) {
         JMenuItem item = new JMenuItem(loadMenuItems[i]);
         item.addActionListener(this);
         loadMenu.add(item);
      }

      for (int i=0; i < atmMenuItems.length; i++) {
         JMenuItem item = new JMenuItem(atmMenuItems[i]);
         item.addActionListener(this);
         atmMenu.add(item);
      }
      
      add(testMenu);
      add(loadMenu);
      add(atmMenu);
   }

   public void actionPerformed(ActionEvent event) {
	   System.out.println(event.getActionCommand());
	   if(mapcardsPanel.containsKey(event.getActionCommand())) {
		   if(event.getActionCommand().equalsIgnoreCase("ATM Txns")) {
			   	ttu.setSplitPanel(mapcardsPanel.get(event.getActionCommand()));   
		   } else{
		   		ttu.setMidPanel(mapcardsPanel.get(event.getActionCommand()));
		   }
	   		ttu.validate();
	   		ttu.repaint();
	   }
   }
}