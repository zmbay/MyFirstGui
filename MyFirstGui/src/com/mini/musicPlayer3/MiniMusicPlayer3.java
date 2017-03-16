////取得MDDI事件，在播放音乐时在新窗口画随机矩形
package com.mini.musicPlayer3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.sound.midi.*;

public class MiniMusicPlayer3 {
	JFrame frame=new JFrame("My first MiniMusicPlayer");
	MyDrawPanel drawPanel;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniMusicPlayer3 mini=new MiniMusicPlayer3();
		mini.go();
	}

	private void go() {
		// TODO Auto-generated method stub
		setGui();
		
		try {
			Sequencer sequencer=MidiSystem.getSequencer();//创建并打开队列
			sequencer.open();
			
			int[] eventsIWant={127};//向sequencer注册事件，注册的方法取用监听者与代表想要临时监听的事件的int数组
			sequencer.addControllerEventListener(drawPanel, eventsIWant);//我们只需要127事件
			
			Sequence seq=new Sequence(Sequence.PPQ,4);//创建队列并track
			Track track=seq.createTrack();
			int r=0;
			for(int i=0;i<61;i+=4){						//创建一堆音符事件
				r=(int)(Math.random()*50+1);
				track.add(makeEvent(144, 1, r, 100, i));//调用makeEvent（）来产生信息和事件
				track.add(makeEvent(176, 1, 127, 0, i));//插入事件编号为127的自定义ControllerEvent(176)，它不会做任何事，只是让我们知道有音符被播放，因为它的tick跟ＮＯＴＥ　ＯＮ是同时进行的
				track.add(makeEvent(128, 1, r, 100, i+2));//然后把它们加到track上
			}
			
			sequencer.setSequence(seq);				//开始播放
			sequencer.setTempoInBPM(220);
			sequencer.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void setGui() {
		// TODO Auto-generated method stub
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawPanel=new MyDrawPanel();
		frame.setContentPane(drawPanel);
		
		frame.setBounds(30,30,300,300);
		frame.setVisible(true);
	}
	
	class MyDrawPanel extends JPanel implements ControllerEventListener{
		boolean msg=false;
		public void controlChange(ShortMessage event){
			msg=true;
			repaint();
		}
		public void paintComponent(Graphics g){
			if(msg){
				Graphics2D g2d=(Graphics2D) g;
				int red=(int) (Math.random()*255);
				int green=(int) (Math.random()*255);
				int blue=(int) (Math.random()*255);
				g.setColor(new Color(red,green,blue));
				
				int height=(int)(Math.random()*120+10);
				int width=(int)(Math.random()*120+10);
				int x=(int)(Math.random()*40+10);
				int y=(int)(Math.random()*40+10);
				
				g.fillRect(x, y, width, height);
				msg=false;
			}
		}
	}
	
	public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
		MidiEvent event=null;
		try {
			ShortMessage message=new ShortMessage(comd, chan, one, two);
			event=new MidiEvent(message, tick);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return event;
	}
}
