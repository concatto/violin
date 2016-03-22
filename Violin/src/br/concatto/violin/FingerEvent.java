package br.concatto.violin;

public class FingerEvent {
	public enum FingerEventType {HOLD, RELEASE};
	
	private int stringIndex;
	private int fingerPosition;
	private FingerEventType eventType;
	
	public FingerEvent(int stringIndex, int fingerPosition, FingerEventType eventType) {
		this.stringIndex = stringIndex;
		this.fingerPosition = fingerPosition;
		this.eventType = eventType;
	}
	
	public int getStringIndex() {
		return stringIndex;
	}
	
	public int getFingerPosition() {
		return fingerPosition;
	}
	
	public FingerEventType getEventType() {
		return eventType;
	}
}
