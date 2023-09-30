package com.bslsk.bin;
/*
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
*/
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class SideFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JList<String> list;
	JList<String> layersList;
	DefaultListModel<String> layers;
	
	JLabel dMode;
	JLabel effectL;
	JPanel panel;
	String[] heading = {"Renderers Added: ",
						"Display Mode:    ",
						"Angle:           ",
						"Scale:           ",
						"Color Mode       "
	};
	String[] values = {"0","1","0","1","Random"};
	public SideFrame() 
	{
		super("Toolbar");
		this.setSize(150,400);
		
		
		this.setLocation(850, 10);
		panel = new JPanel();
		list = new JList<String>();
		updateList(values);
		layers = new DefaultListModel<String>();
		layersList = new JList<String>(layers);
		panel.add(list);
		panel.add(new JLabel("Layers:"));
		panel.add(layersList);
		dMode = new JLabel("Drawing Mode: DISABLED");
		effectL = new JLabel("Active Effect:");
		panel.add(dMode);
		this.add(panel);
		this.setVisible(true);
		
	}
	public void updateList(String[] v)
	{
		values = v;
		list.removeAll();
		String[] nL = new String[v.length];
		for(int x = 0; x < nL.length; x++)
		{
			nL[x] = heading[x] + values[x];
		}
		list.setListData(nL);
		//list.updateUI();
	}
	public void removeItem()
	{
		layers.remove(layers.getSize()-1);
	}
	public void addItem(String item)
	{
		layers.addElement(item);
	}
	public void setDrawingMode(boolean dm)
	{
		if(dm)
			dMode.setText("Drawing Mode: ENABLED");
		else if(!dm)
			dMode.setText("Drawing Mode: DISABLED");
	}
}
