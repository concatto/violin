package br.concatto.violin;

import java.io.IOException;
import java.net.URL;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class SoundManager {
	private int channel;
	
	private Synthesizer synth;
	private MidiChannel channels[];
	
	public SoundManager(int channel) throws MidiUnavailableException {
		this.channel = channel;
		synth = MidiSystem.getSynthesizer();
		synth.open();
		channels = synth.getChannels();
		
		Instrument[] instruments;
		
		try {
			URL bank = SoundManager.class.getClassLoader().getResource("genusrmusescore.sf2");
			
			instruments = MidiSystem.getSoundbank(bank).getInstruments();
		} catch (InvalidMidiDataException | IOException e) {
			instruments = synth.getDefaultSoundbank().getInstruments();
			e.printStackTrace();
		}
		
		Instrument violin = instruments[40];
		synth.loadInstrument(violin);
		channels[channel].programChange(violin.getPatch().getProgram());
	}
	
	public SoundManager() throws MidiUnavailableException {
		this(0);
	}
	
	public void on(int note) {
		channels[channel].noteOn(note, 127);
	}
	
	public void off(int note) {
		channels[channel].noteOff(note);
	}

	public void allOff() {
		channels[channel].allNotesOff();
	}
}
