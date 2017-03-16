package com.twobutton.tui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test gui=new Test();
		gui.go();
	}

	private void go() {
		// TODO Auto-generated method stub
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyDrawPanel panel=new MyDrawPanel();
		//panel.setBackground(Color.blue);
		frame.getContentPane().add(BorderLayout.CENTER,panel);
//		
		frame.setSize(800, 600);
		frame.setVisible(true);
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
			g.setColor(Color.orange);
			g.fillRect(70,70,100,100);
			
	//////////////////////////////////////////////////////////////////
//			Image image=new ImageIcon("res\\a6174521_0240.jpg").getImage();
//			g.drawImage(image, 280, 100 , this);

		}
	}
}
