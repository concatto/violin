package br.concatto.violin;

import javax.sound.midi.MidiUnavailableException;

public class Violin {
	private static final int LOWEST_G = 55;
	private static final int NOTES_PER_STRING = 7;
	
	private ViolinString[] strings = new ViolinString[4];
	private SoundManager soundManager;
	private int lowestNote;
	private int currentString;
	private boolean bowing = false;
	
	public Violin() {
		this(LOWEST_G);
		
		try {
			soundManager = new SoundManager(0);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			System.out.println("No MIDI device available");
		}
		
		for (int i = 0; i < strings.length; i++) {
			strings[i] = new ViolinString();
		}
	}
	
	public Violin(int lowestNote) {
		this.lowestNote = lowestNote;
	}
	
	private void restartBowing() {
		startBowing(currentString);
	}
	
	public void startBowing(int stringIndex) {
		stopBowing();
		currentString = stringIndex;
		
		int note = lowestNote + (stringIndex * NOTES_PER_STRING) + strings[stringIndex].getHighestPosition();
		soundManager.on(note);
		
		bowing = true;
	}
	
	public void stopBowing() {
		soundManager.allOff();
		bowing = false;
	}
	
	public void holdFinger(int stringIndex, int position) {
		if (!strings[stringIndex].isHeld(position)) {
			strings[stringIndex].hold(position);
			if (bowing && currentString == stringIndex) {
				restartBowing();
			}
		}
	}
	
	public void releaseFinger(int stringIndex, int position) {
		strings[stringIndex].release(position);
		if (bowing && currentString == stringIndex) {
			restartBowing();
		}
	}
}
