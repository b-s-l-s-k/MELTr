package com.bslsk.bin;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bslsk.info.Assets;
import com.bslsk.info.Preferences;
import com.bslsk.ui.TPanel;
import com.bslsk.ui.TimeLine;
import java.awt.Color;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class CommandFrame extends JFrame implements ChangeListener
{

	private static final long serialVersionUID = -8726129841735251587L;

			
	
	GFrame main0;
	public CommandFrame()
	{
		super("MELTR - Command Center");
		main0 = new GFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		//setOpacity(0.5f);
		setExtendedState(Frame.ICONIFIED);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JDesktopPane desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane);
		
		JInternalFrame angleFrame = new JInternalFrame("Sliders");
		angleFrame.setBorder(null);
		angleFrame.setIconifiable(true);
		angleFrame.setBounds(21, 11, 349, 376);
		desktopPane.add(angleFrame);
		SpringLayout springLayout = new SpringLayout();
		angleFrame.getContentPane().setLayout(springLayout);
		
		JLabel lblAngle = new JLabel("Angle");
		springLayout.putConstraint(SpringLayout.NORTH, lblAngle, 6, SpringLayout.NORTH, angleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblAngle, 0, SpringLayout.WEST, angleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblAngle, 348, SpringLayout.WEST, angleFrame.getContentPane());
		angleFrame.getContentPane().add(lblAngle);
		
		JSlider sliderAngle = new JSlider();
		springLayout.putConstraint(SpringLayout.NORTH, sliderAngle, 26, SpringLayout.NORTH, angleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, sliderAngle, 0, SpringLayout.WEST, angleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, sliderAngle, 348, SpringLayout.WEST, angleFrame.getContentPane());
		sliderAngle.setMinimum(Assets.CONSTRAINTS[0].bounds[0]);
		sliderAngle.setMaximum(Assets.CONSTRAINTS[0].bounds[1]);
		sliderAngle.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Assets.CONSTRAINTS[0].setParam(((Integer)evt.getNewValue()).intValue());
				
			}
			
		});
			
		
		angleFrame.getContentPane().add(sliderAngle);
		
		JSlider sliderScale = new JSlider();
		springLayout.putConstraint(SpringLayout.WEST, sliderScale, 0, SpringLayout.WEST, lblAngle);
		springLayout.putConstraint(SpringLayout.EAST, sliderScale, 348, SpringLayout.WEST, angleFrame.getContentPane());
		sliderScale.setMinimum(Assets.CONSTRAINTS[1].bounds[0]);
		sliderScale.setMaximum(Assets.CONSTRAINTS[1].bounds[1]);
		sliderScale.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Assets.CONSTRAINTS[1].setParam(((Integer)evt.getNewValue()).intValue());
				
			}
			
		});
		angleFrame.getContentPane().add(sliderScale);
		
		JLabel lblScale = new JLabel("Scale");
		springLayout.putConstraint(SpringLayout.NORTH, sliderScale, 6, SpringLayout.SOUTH, lblScale);
		springLayout.putConstraint(SpringLayout.NORTH, lblScale, 6, SpringLayout.SOUTH, sliderAngle);
		springLayout.putConstraint(SpringLayout.WEST, lblScale, 0, SpringLayout.WEST, lblAngle);
		angleFrame.getContentPane().add(lblScale);
		
		JSlider sliderX = new JSlider();
		springLayout.putConstraint(SpringLayout.WEST, sliderX, 0, SpringLayout.WEST, angleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, sliderX, 0, SpringLayout.EAST, lblAngle);
		sliderX.setMinimum(Assets.CONSTRAINTS[2].bounds[0]);
		sliderX.setMaximum(Assets.CONSTRAINTS[2].bounds[1]);
		sliderX.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Assets.CONSTRAINTS[2].setParam(((Integer)evt.getNewValue()).intValue());
				
			}
			
		});
		angleFrame.getContentPane().add(sliderX);
		
		JSlider sliderY = new JSlider();
		springLayout.putConstraint(SpringLayout.WEST, sliderY, 0, SpringLayout.WEST, lblAngle);
		springLayout.putConstraint(SpringLayout.SOUTH, sliderY, -120, SpringLayout.SOUTH, angleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, sliderY, 0, SpringLayout.EAST, lblAngle);
		sliderY.setMinimum(Assets.CONSTRAINTS[3].bounds[0]);
		sliderY.setMaximum(Assets.CONSTRAINTS[3].bounds[1]);
		sliderY.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Assets.CONSTRAINTS[3].setParam(((Integer)evt.getNewValue()).intValue());
				
			}
			
		});
		angleFrame.getContentPane().add(sliderY);
		
		JLabel lblNewLabel = new JLabel("Transform Y");
		springLayout.putConstraint(SpringLayout.NORTH, sliderY, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, angleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, sliderX, -6, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -157, SpringLayout.SOUTH, angleFrame.getContentPane());
		angleFrame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Transform X");
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1, -6, SpringLayout.NORTH, sliderX);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_1, 0, SpringLayout.EAST, lblNewLabel);
		angleFrame.getContentPane().add(lblNewLabel_1);
		
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		internalFrame.setBounds(380, 11, 175, 360);
		desktopPane.add(internalFrame);
		SpringLayout springLayout_1 = new SpringLayout();
		internalFrame.getContentPane().setLayout(springLayout_1);
		
		JButton bMode0 = new JButton("Normal");
		springLayout_1.putConstraint(SpringLayout.NORTH, bMode0, 0, SpringLayout.NORTH, internalFrame.getContentPane());
		springLayout_1.putConstraint(SpringLayout.WEST, bMode0, 0, SpringLayout.WEST, internalFrame.getContentPane());
		springLayout_1.putConstraint(SpringLayout.SOUTH, bMode0, -268, SpringLayout.SOUTH, internalFrame.getContentPane());
		springLayout_1.putConstraint(SpringLayout.EAST, bMode0, -85, SpringLayout.EAST, internalFrame.getContentPane());
		bMode0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		internalFrame.getContentPane().add(bMode0);
		
		JButton bMode1 = new JButton("Reflect");
		springLayout_1.putConstraint(SpringLayout.NORTH, bMode1, 0, SpringLayout.NORTH, bMode0);
		springLayout_1.putConstraint(SpringLayout.WEST, bMode1, 8, SpringLayout.EAST, bMode0);
		springLayout_1.putConstraint(SpringLayout.SOUTH, bMode1, 0, SpringLayout.SOUTH, bMode0);
		springLayout_1.putConstraint(SpringLayout.EAST, bMode1, 0, SpringLayout.EAST, internalFrame.getContentPane());
		internalFrame.getContentPane().add(bMode1);
		
		JButton bMode2 = new JButton("DoubleReflect");
		springLayout_1.putConstraint(SpringLayout.NORTH, bMode2, 6, SpringLayout.SOUTH, bMode0);
		springLayout_1.putConstraint(SpringLayout.WEST, bMode2, 0, SpringLayout.WEST, bMode0);
		springLayout_1.putConstraint(SpringLayout.SOUTH, bMode2, 68, SpringLayout.SOUTH, bMode0);
		springLayout_1.putConstraint(SpringLayout.EAST, bMode2, 74, SpringLayout.WEST, bMode0);
		internalFrame.getContentPane().add(bMode2);
		
		JButton bMode3 = new JButton("Quad");
		springLayout_1.putConstraint(SpringLayout.NORTH, bMode3, 6, SpringLayout.SOUTH, bMode1);
		springLayout_1.putConstraint(SpringLayout.WEST, bMode3, 0, SpringLayout.WEST, bMode1);
		springLayout_1.putConstraint(SpringLayout.SOUTH, bMode3, 0, SpringLayout.SOUTH, bMode2);
		springLayout_1.putConstraint(SpringLayout.EAST, bMode3, -3, SpringLayout.EAST, bMode1);
		internalFrame.getContentPane().add(bMode3);
		
		JButton bMode4 = new JButton("Glitch");
		springLayout_1.putConstraint(SpringLayout.NORTH, bMode4, 6, SpringLayout.SOUTH, bMode2);
		springLayout_1.putConstraint(SpringLayout.WEST, bMode4, 0, SpringLayout.WEST, internalFrame.getContentPane());
		springLayout_1.putConstraint(SpringLayout.SOUTH, bMode4, 68, SpringLayout.SOUTH, bMode2);
		springLayout_1.putConstraint(SpringLayout.EAST, bMode4, -85, SpringLayout.EAST, internalFrame.getContentPane());
		internalFrame.getContentPane().add(bMode4);
		
		JButton bMode5 = new JButton("Life");
		springLayout_1.putConstraint(SpringLayout.NORTH, bMode5, 6, SpringLayout.SOUTH, bMode2);
		springLayout_1.putConstraint(SpringLayout.WEST, bMode5, 82, SpringLayout.WEST, bMode0);
		springLayout_1.putConstraint(SpringLayout.SOUTH, bMode5, 0, SpringLayout.SOUTH, bMode4);
		springLayout_1.putConstraint(SpringLayout.EAST, bMode5, 0, SpringLayout.EAST, bMode3);
		internalFrame.getContentPane().add(bMode5);
		
		JInternalFrame internalFrame_1 = new JInternalFrame("New JInternalFrame");
		internalFrame_1.setBounds(21, 392, 516, 95);
		desktopPane.add(internalFrame_1);
		internalFrame_1.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("LINE");
		btnNewButton.setBounds(0, 0, 89, 65);
		internalFrame_1.getContentPane().add(btnNewButton);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.setBounds(91, 0, 89, 65);
		internalFrame_1.getContentPane().add(btnClear);
		
		JButton btnColor = new JButton("COLOR");
		btnColor.setBounds(183, 0, 89, 65);
		internalFrame_1.getContentPane().add(btnColor);
		
		JButton btnGlitch = new JButton("GLITCH");
		btnGlitch.setBounds(271, 0, 89, 65);
		internalFrame_1.getContentPane().add(btnGlitch);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelete.setBounds(411, 0, 89, 65);
		internalFrame_1.getContentPane().add(btnDelete);
		internalFrame_1.setVisible(true);
		internalFrame.setVisible(true);
		angleFrame.setVisible(true);
		angleFrame.getGraphics();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.LIGHT_GRAY);
		Preferences.initPrefs();
		this.setSize(612,537);
		this.setVisible(true);
		
	}

	public static void main(String[] args) 
	{ 
		try {
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new CommandFrame();
		}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		
		
	}
}
