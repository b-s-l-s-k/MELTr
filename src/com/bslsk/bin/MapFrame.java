package com.bslsk.bin;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import com.bslsk.info.Key;
import com.bslsk.info.KeyMapper;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

public class MapFrame extends JFrame implements ActionListener, KeyListener, ListSelectionListener{

	KeyMapper keys;
	char cPressed;
	int iPressed;
	
	boolean choosing;
	JSplitPane split;
	
		JPanel panelLeft;
			JScrollPane scroll;
			JList<String> listBox;
				DefaultListModel<String> list;
			JButton btnAdd, btnRemove;
		JPanel panelRight;
			JLabel lblHeading;
			JButton btnNew;
			JComboBox<String> comboType;
			JComboBox<String> comboS;
			JComboBox<String> comboD;
			JButton btnSave;
	String[] types = new String[] 
	{
			"ADD_CONTEXT", 
			"REMOVE_CONTEXT",
			"SET_SETTING", 
			"SET_DRAW", 
			"SET_EFFECT", 
			"SET_SHIFTER", 
			"KEY_DOWN"
	};
	String[] contexts = new String[]
	{
			"CONTEXT_LINE",
			"CONTEXT_CLEAR",  
			"CONTEXT_COLOR",  
			"CONTEXT_GLITCH", 
			"CONTEXT_WORD", 
			"CONTEXT_IMAGE", 
			"CONTEXT_CIRCLE"	
	};
	public MapFrame() 
	{
		super("Edit KeyMap");
		this.setDefaultLookAndFeelDecorated(false);
		split = new JSplitPane();
		
		panelLeft = new JPanel();
		//panelLeft.setLayout(new CardLayout());
		list = new DefaultListModel<String>();
		listBox = new JList<String>(list);
		listBox.addListSelectionListener(this);
		scroll = new JScrollPane(listBox);
		btnAdd = new JButton("+");
		btnRemove = new JButton("-");
		btnAdd.addActionListener(
				   new ActionListener()
				   {
				       public void actionPerformed(ActionEvent e)
				       {
				    	   //removeKeyListener(this);
							choosing = true;
							//addKeyListener(this);
				       }
				   }
				);
		//btnAdd.addActionListener(this);
		btnRemove.addActionListener(this);
		panelLeft.add(scroll);
		panelLeft.add(btnAdd);
		panelLeft.add(btnRemove);
		split.setLeftComponent(panelLeft);
		
		panelRight = new JPanel();
		
		lblHeading = new JLabel("Choose Type for Key: ");
		btnNew = new JButton("New Action");
		btnNew.addActionListener(
				   new ActionListener()
				   {
				       public void actionPerformed(ActionEvent e)
				       {
							if(choosing)
							{
								choosing = false;
								//this.removeKeyListener(this);
								list.addElement(iPressed+"");
								listBox.setSelectedIndex(list.getSize()-1);
							}
				       }
				   }
				);
		comboType = new JComboBox<String>(types);
		comboType.addActionListener(
				   new ActionListener()
				   {
				       public void actionPerformed(ActionEvent e)
				       {
				    	   comboS = null;
							comboD = null;
							
							if(0 == comboType.getSelectedIndex())
							{
								comboS = new JComboBox<String>(contexts);
								comboS.addActionListener(this);
							}
				       }
				   }
				);
		
		btnSave= new JButton("Confirm");
		panelRight.add(lblHeading);
		
		panelRight.add(btnNew);
		panelRight.add(comboType);
		panelRight.add(btnSave);
		
		split.setRightComponent(panelRight);
		this.getContentPane().add(split);
		split.addKeyListener(this);
		
		this.setSize(getPreferredSize());
		this.setVisible(true);
		
	}

	public MapFrame(KeyMapper k) 
	{
		this();
		keys = k;
		buildList();
	}

	private void buildList() 
	{
		for(Key k: keys.keys)
			list.addElement(k.getKey()+"");
		
	}
	private void buildOptions()
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(choosing)
		{
			cPressed = e.getKeyChar();
			iPressed = e.getKeyCode();
			lblHeading.setText("Choose Type for Key: " + cPressed + "("+iPressed+")");
		}
		System.out.println(""+e.getKeyChar());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{

		if(btnNew== (JButton)e.getSource())
		{
			if(choosing)
			{
				choosing = false;
				this.removeKeyListener(this);
				list.addElement(iPressed+"");
				listBox.setSelectedIndex(list.getSize()-1);
			}
		}
		else if(comboType== (JComboBox<String>)e.getSource())
		{
			comboS = null;
			comboD = null;
			
			if(0 == comboType.getSelectedIndex())
			{
				comboS = new JComboBox<String>(contexts);
				comboS.addActionListener(this);
			}
		}
		else if(comboS== (JComboBox<String>)e.getSource())
		{
			comboD = null;
			
		}
		
	}


	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		
		
	}

}
