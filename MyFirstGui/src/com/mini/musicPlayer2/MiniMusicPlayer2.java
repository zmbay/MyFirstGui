//取得MDDI事件，在播放音乐时控制台输出
package com.mini.musicPlayer2;
import javax.sound.midi.*;

public class MiniMusicPlayer2 implements ControllerEventListener{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniMusicPlayer2 mini=new MiniMusicPlayer2();
		mini.go();
	}
	
	private void go() {
		// TODO Auto-generated method stub
		try {
			Sequencer sequencer=MidiSystem.getSequencer();//创建并打开队列
			sequencer.open();
			
			int[] eventsIWant={127};//向sequencer注册事件，注册的方法取用监听者与代表想要临时监听的事件的int数组
			sequencer.addControllerEventListener(this, eventsIWant);//我们只需要127事件
			
			Sequence seq=new Sequence(Sequence.PPQ,4);//创建队列并track
			Track track=seq.createTrack();
			
			for(int i=5;i<61;i+=4){						//创建一堆连续的音符事件
				track.add(makeEvent(144, 1, i, 100, i));//调用makeEvent（）来产生信息和事件
				track.add(makeEvent(176, 1, 127, 0, i));//插入事件编号为127的自定义ControllerEvent(176)，它不会做任何事，只是让我们知道有音符被播放，因为它的tick跟ＮＯＴＥ　ＯＮ是同时进行的
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

	public void controlChange(ShortMessage event){
		System.out.println("la");
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
