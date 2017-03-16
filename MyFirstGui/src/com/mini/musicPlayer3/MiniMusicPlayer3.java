////ȡ��MDDI�¼����ڲ�������ʱ���´��ڻ��������
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
			Sequencer sequencer=MidiSystem.getSequencer();//�������򿪶���
			sequencer.open();
			
			int[] eventsIWant={127};//��sequencerע���¼���ע��ķ���ȡ�ü������������Ҫ��ʱ�������¼���int����
			sequencer.addControllerEventListener(drawPanel, eventsIWant);//����ֻ��Ҫ127�¼�
			
			Sequence seq=new Sequence(Sequence.PPQ,4);//�������в�track
			Track track=seq.createTrack();
			int r=0;
			for(int i=0;i<61;i+=4){						//����һ�������¼�
				r=(int)(Math.random()*50+1);
				track.add(makeEvent(144, 1, r, 100, i));//����makeEvent������������Ϣ���¼�
				track.add(makeEvent(176, 1, 127, 0, i));//�����¼����Ϊ127���Զ���ControllerEvent(176)�����������κ��£�ֻ��������֪�������������ţ���Ϊ����tick���Σϣԣš��ϣ���ͬʱ���е�
				track.add(makeEvent(128, 1, r, 100, i+2));//Ȼ������Ǽӵ�track��
			}
			
			sequencer.setSequence(seq);				//��ʼ����
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
