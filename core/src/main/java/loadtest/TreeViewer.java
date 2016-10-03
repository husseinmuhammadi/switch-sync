package loadtest;

import java.io.*;    
import javax.swing.*;    
import javax.swing.tree.*;    
import java.awt.*;    
import java.awt.event.*;    
import java.util.*;    
import javax.swing.event.*;    
public class TreeViewer    
{    
    JFrame frm;    
    JFileChooser file_c;    
    JTree f_tree;    
    public TreeViewer(String path)    
    {    
        frm=new JFrame("Jtree Demo");       
        Container cp=frm.getContentPane();      
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(path);    
        f_tree=new JTree(top);    
        JScrollPane treeView = new JScrollPane(f_tree);    
        f_tree.addTreeSelectionListener(new TreeSelectionListener()    
        {    
            public void valueChanged(TreeSelectionEvent e)    
            {    
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)                                              
                f_tree.getLastSelectedPathComponent();    
                frm.setTitle("You have selected : "+ node.toString());    
                if (node != null) {
                    File t_file=new File(node.toString());    
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
        });    
        
        JSplitPane splitPane =
            new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            		treeView,
                           new JPanel());
        splitPane.setDividerLocation(300);
        
        frm.add(splitPane);    
        frm.setSize(800,600);    
        frm.setVisible(true);    
    }    
   
    public static void main(String args[])    
    {       
        try    
        {    
            new TreeViewer("./atmmessages");           
        }    
        catch(Exception e)    
        {    
            System.out.println("Plese Specify path from Command line");    
        }           
    }    
}