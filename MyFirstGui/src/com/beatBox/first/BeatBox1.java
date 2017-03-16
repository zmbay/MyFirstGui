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
	
	String[] instrumentNames={"大鼓：Bass Drum","闭合击镲：Closed Hi-Hat",
			"开音踩镲：Open Hi-Hat","原声军鼓：Acoustic Snare","强音钹：Crash Cymbal",
			"拍手：Hand Clap","高音桶鼓：High Tom","高音邦戈：Hi Bongo","沙球：Maracas",
			"口哨：Whistle","低音康加：Low Conga","牛铃：Cowbell","颤音叉：Vibraslap",
			"低音桶鼓：Low-min Tom","高音拉丁打铃：High Agogo","高音康加：Open Hi Conga"};
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
	
	//一般的MIDI设置程序
	public void setUpMidi(){
		try {
			sequencer=MidiSystem.getSequencer();//创建并打开队列
			sequencer.open();
			sequence =new Sequence(Sequence.PPQ,4);//创建队列并track
			track=sequence.createTrack();
			sequencer.setTempoInBPM(220);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//将复选框状态转换为MIDI事件并添加到track
	public void buildTrackAndStart(){
		int[] trackList=null;//创建出16个元素的数组来存储一项乐器的值，如果该节要演奏，其值会是关键字，否则值为0
		sequence.deleteTrack(track);
		track=sequence.createTrack();
		
		for(int i=0;i<16;i++){//对每个乐器执行一次
			trackList=new int[16];
			int key=instruments[i];//对每拍执行一次
			for(int j=0;j<16;j++){
				JCheckBox jc=(JCheckBox)checkBoxList.get(j+i*16);
				if(jc.isSelected()){
					trackList[j]=key;
				}else{
					trackList[j]=0;
				}
			}//内部循环到此为止
			makeTrack(trackList);//创建此乐器的事件并添加到track上
			track.add(makeEvent(176, 1, 127, 0, 16));
		}//外部循环到此为止
		
		track.add(makeEvent(192, 9, 1, 0, 15));//确保第16拍有事件，否则beatbox不会重复播放
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
							//						频道		音符		音道		时间
	public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){//频道代表不同的演奏者，例如1号吉他2号Bass；音符0-127代表不同 的音高；音道表示用多大的音道按下，0几乎听不到，100差不多。
		MidiEvent event=null;
		try {
			ShortMessage message=new ShortMessage(comd, chan, one, two);
			event=new MidiEvent(message, tick);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return event;
	}
	
	//创建某项乐器的所有事件
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
