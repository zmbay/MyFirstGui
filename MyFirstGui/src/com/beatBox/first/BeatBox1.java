package com.beatBox.first;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.midi.*;

public class BeatBox1 {
	JFrame frame;
	JPanel mainPanel;
	ArrayList<JCheckBox> checkBoxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	
	String[] instrumentNames={"��ģ�Bass Drum","�պϻ��Closed Hi-Hat",
			"�������Open Hi-Hat","ԭ�����ģ�Acoustic Snare","ǿ���ࣺCrash Cymbal",
			"���֣�Hand Clap","����Ͱ�ģ�High Tom","������꣺Hi Bongo","ɳ��Maracas",
			"���ڣ�Whistle","�������ӣ�Low Conga","ţ�壺Cowbell","�����棺Vibraslap",
			"����Ͱ�ģ�Low-min Tom","�����������壺High Agogo","�������ӣ�Open Hi Conga"};
	int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	public static void main(String[] args){
		new BeatBox1().buildGui();
	}
	public void buildGui(){
		frame=new JFrame("BeatBox1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel background=new JPanel(new BorderLayout());
		//background.setBackground(Color.DARK_GRAY);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		Box buttonBox=new Box(BoxLayout.Y_AXIS);
		Box nameBox=new Box(BoxLayout.Y_AXIS);
		checkBoxList =new ArrayList<JCheckBox>();
		
		JButton start=new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);
		JButton stop=new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);
		JButton tempoUp=new JButton("Tempo Up");
		tempoUp.addActionListener(new MyTempoUpListener());
		buttonBox.add(tempoUp);
		JButton tempoDown=new JButton("Tempo Down");
		tempoDown.addActionListener(new MyTempoDownListener());
		buttonBox.add(tempoDown);
		for(int i=0;i<instrumentNames.length;i++){
			nameBox.add(new Label(instrumentNames[i]));
		}
		background.add(BorderLayout.EAST,buttonBox);
		background.add(BorderLayout.WEST, nameBox);
		frame.getContentPane().add(background);
		
		GridLayout grid=new GridLayout(16,16);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel=new JPanel(grid);
		background.add(BorderLayout.CENTER,mainPanel);
		for(int i=0;i<256;i++){
			JCheckBox c=new JCheckBox();
			c.setSelected(false);
			checkBoxList.add(c);
			mainPanel.add(c);
		}
		
		setUpMidi();
		frame.setBounds(400, 100, 300, 300);
		frame.pack();
		frame.setVisible(true);
	}
	
	//һ���MIDI���ó���
	public void setUpMidi(){
		try {
			sequencer=MidiSystem.getSequencer();//�������򿪶���
			sequencer.open();
			sequence =new Sequence(Sequence.PPQ,4);//�������в�track
			track=sequence.createTrack();
			sequencer.setTempoInBPM(220);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//����ѡ��״̬ת��ΪMIDI�¼�����ӵ�track
	public void buildTrackAndStart(){
		int[] trackList=null;//������16��Ԫ�ص��������洢һ��������ֵ������ý�Ҫ���࣬��ֵ���ǹؼ��֣�����ֵΪ0
		sequence.deleteTrack(track);
		track=sequence.createTrack();
		
		for(int i=0;i<16;i++){//��ÿ������ִ��һ��
			trackList=new int[16];
			int key=instruments[i];//��ÿ��ִ��һ��
			for(int j=0;j<16;j++){
				JCheckBox jc=(JCheckBox)checkBoxList.get(j+i*16);
				if(jc.isSelected()){
					trackList[j]=key;
				}else{
					trackList[j]=0;
				}
			}//�ڲ�ѭ������Ϊֹ
			makeTrack(trackList);//�������������¼�����ӵ�track��
			track.add(makeEvent(176, 1, 127, 0, 16));
		}//�ⲿѭ������Ϊֹ
		
		track.add(makeEvent(192, 9, 1, 0, 15));//ȷ����16�����¼�������beatbox�����ظ�����
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoFactor(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public class MyStartListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			buildTrackAndStart();
		}
	}
	
	public class MyStopListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			sequencer.stop();
		}
		}
	
	public class MyTempoUpListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor=sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor*1.03));
		}
	}
	
	public class MyTempoDownListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor=sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor*0.5));
		}
	}
							//						Ƶ��		����		����		ʱ��
	public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){//Ƶ������ͬ�������ߣ�����1�ż���2��Bass������0-127����ͬ �����ߣ�������ʾ�ö����������£�0������������100��ࡣ
		MidiEvent event=null;
		try {
			ShortMessage message=new ShortMessage(comd, chan, one, two);
			event=new MidiEvent(message, tick);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return event;
	}
	
	//����ĳ�������������¼�
	public void makeTrack(int[] list){
		for(int i=0;i<16;i++){
			int key=list[i];
			if(key!=0){
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i+1));
			}
		}
	}
}
