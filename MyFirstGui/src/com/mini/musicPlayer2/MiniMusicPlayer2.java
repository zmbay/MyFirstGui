//ȡ��MDDI�¼����ڲ�������ʱ����̨���
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
			Sequencer sequencer=MidiSystem.getSequencer();//�������򿪶���
			sequencer.open();
			
			int[] eventsIWant={127};//��sequencerע���¼���ע��ķ���ȡ�ü������������Ҫ��ʱ�������¼���int����
			sequencer.addControllerEventListener(this, eventsIWant);//����ֻ��Ҫ127�¼�
			
			Sequence seq=new Sequence(Sequence.PPQ,4);//�������в�track
			Track track=seq.createTrack();
			
			for(int i=5;i<61;i+=4){						//����һ�������������¼�
				track.add(makeEvent(144, 1, i, 100, i));//����makeEvent������������Ϣ���¼�
				track.add(makeEvent(176, 1, 127, 0, i));//�����¼����Ϊ127���Զ���ControllerEvent(176)�����������κ��£�ֻ��������֪�������������ţ���Ϊ����tick���Σϣԣš��ϣ���ͬʱ���е�
				track.add(makeEvent(128, 1, i, 100, i+2));//Ȼ������Ǽӵ�track��
			}
			
			sequencer.setSequence(seq);				//��ʼ����
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
