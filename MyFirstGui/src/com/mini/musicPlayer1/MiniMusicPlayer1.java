//只实现音乐播放
package com.mini.musicPlayer1;

import javax.sound.midi.*;
import javax.sound.midi.ShortMessage;

public class MiniMusicPlayer1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Sequencer sequencer=MidiSystem.getSequencer();//创建并打开队列
			sequencer.open();
			
			Sequence seq=new Sequence(Sequence.PPQ,4);//创建队列并track
			Track track=seq.createTrack();
			
			for(int i=5;i<61;i+=4){						//创建一堆连续的音符事件
				track.add(makeEvent(144, 1, i, 100, i));//调用makeEvent（）来产生信息和事件
				track.add(makeEvent(128, 1, i, 100, i+2));//然后把它们加到track上
			}
			
			sequencer.setSequence(seq);				//开始播放
			sequencer.setTempoInBPM(220);
			sequencer.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
