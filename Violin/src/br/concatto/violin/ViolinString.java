package br.concatto.violin;

public class ViolinString {
	private static final int POSITIONS = 6;
	private int state = 0;
	
	public void hold(int position) {
		state = state | (1 << position);
	}
	
	public void release(int position) {
		state = state & ~(1 << position);
	}
	
	public boolean isHeld(int position) {
		return ((state >> position) & 1) == 1;
	}
	
	/**
	 * Finds the highest position with a finger held.
	 * @return the highest position. 0 is an open string,
	 * 1 is the lowest position and 6 is the highest.
	 */
	public int getHighestPosition() {
		for (int i = POSITIONS - 1; i >= 0; i--) {
			if (((state >> i) & 1) == 1) {
				return i + 1;
			}
		}
		
		return 0;
	}
}
