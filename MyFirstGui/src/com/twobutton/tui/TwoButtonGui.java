package com.twobutton.tui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TwoButtonGui {
	JFrame frame;
	JLabel label;
	int x=70,y=70;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TwoButtonGui gui=new TwoButtonGui();
		gui.go();
	}

	public void go() {
		// TODO Auto-generated method stub
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton labelButton=new JButton("change label");
		labelButton.addActionListener(new LabelListener());
	
		JButton colorButton=new JButton("change circle");
		colorButton.addActionListener(new ColorListener());
		
		label=new JLabel("I'm a label");
		MyDrawPanel drawPanel=new MyDrawPanel();
		
		frame.getContentPane().add(BorderLayout.SOUTH,colorButton);
		frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
		frame.getContentPane().add(BorderLayout.EAST,labelButton);	
		frame.getContentPane().add(BorderLayout.WEST,label);
		
		frame.setSize(300, 300);
		frame.setVisible(true);	
		
		for(int i=0;i<130;i++){
			x++;
			y++;
			drawPanel.repaint();
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	class LabelListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			label.setText("ouch");
		}
	}
	
	class ColorListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			frame.repaint();
		}
	}
	
	class MyDrawPanel extends JPanel{
		public void paintComponent(Graphics g){
//			Graphics2D g2d=(Graphics2D) g;
//			int red=(int) (Math.random()*255);
//			int green=(int) (Math.random()*255);
//			int blue=(int) (Math.random()*255);
//			Color startColor=new Color(red,green,blue);
//			red=(int) (Math.random()*255);
//			green=(int) (Math.random()*255);
//			blue=(int) (Math.random()*255);
//			Color endColor=new Color(red,green,blue);
//			
//			GradientPaint gradient=new GradientPaint(70, 70, startColor, 150, 150, endColor);
//			g2d.setPaint(gradient);
//			g2d.fillOval(70, 70, 100, 100);
			
	////////////////////////////////////////////////////////////////
			g.setColor(Color.WHITE);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			g.setColor(Color.orange);
			g.fillRect(x,y,100,100);
			
	//////////////////////////////////////////////////////////////////
//			Image image=new ImageIcon("res\\a6174521_0240.jpg").getImage();
//			g.drawImage(image, 280, 100 , this);

		}
	}
}
