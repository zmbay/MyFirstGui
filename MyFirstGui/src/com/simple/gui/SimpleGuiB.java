package com.simple.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SimpleGuiB implements ActionListener {
	JButton button;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		button.setText("I have been clicked");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleGuiB gui=new SimpleGuiB();
		gui.go();
	}
	
	public void go(){
		JFrame frame=new JFrame();
		button=new JButton("click button");
		frame.getContentPane().add(button);
		button.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}

}
