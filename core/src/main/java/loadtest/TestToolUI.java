package loadtest;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class TestToolUI extends JFrame {
	private JComponent midPanel;
	JPanel mainPanel=new JPanel();
	JPanel logPanel=new JPanel();
	
	public TestToolUI(String title) {
		super(title);
		mainPanel.setLayout(new GridLayout(2,1));
		mainPanel.setSize(750, 400);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setMidPanel (JComponent midPanel) {
		if(this.midPanel != null) {
			mainPanel.remove(this.midPanel);
			mainPanel.remove(this.midPanel);
		}
		this.setContentPane(mainPanel);
		this.midPanel = midPanel;
		mainPanel.add(this.midPanel, 0);
	}

	public void setSplitPanel (JComponent jsp) {
		//this.remove(mainPanel);
		//jsp.setSize(750, 400);
		//this.setContentPane(jsp);
		mainPanel.remove(this.midPanel);
		this.midPanel = jsp;
		mainPanel.add(jsp, 0);
	}
	
	public void setlogPanel (JPanel logPanel) {
		this.logPanel = logPanel;
		mainPanel.add(new JScrollPane(this.logPanel));
	}
}
