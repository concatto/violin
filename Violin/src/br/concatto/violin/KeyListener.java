package br.concatto.violin;

import java.util.Optional;
import java.util.function.Consumer;

import br.concatto.violin.FingerEvent.FingerEventType;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyListener implements EventHandler<KeyEvent> {
	private Runnable bowStopAction;
	private Consumer<Integer> bowStartAction;
	private Consumer<FingerEvent> fingerAction;
	
	private KeyCode[] bowingKeys = {KeyCode.DIVIDE, KeyCode.NUMPAD8, KeyCode.NUMPAD5, KeyCode.NUMPAD2};
	private KeyCode[][] noteMatrix = new KeyCode[4][6];
	private int currentBowingIndex = -1;
	
	public KeyListener() {
		String[] keys = {
				"123456", "qwerty", "asdfgh", "zxcvbn"
		};
		
		for (int i = 0; i < noteMatrix.length; i++) {
			for (int j = 0; j < noteMatrix[i].length; j++) {
				noteMatrix[i][j] = KeyCode.getKeyCode(String.valueOf(keys[i].charAt(j)).toUpperCase());
			}
		}
	}
	
	@Override
	public void handle(KeyEvent event) {
		boolean pressed = event.getEventType() == KeyEvent.KEY_PRESSED;
		
		//Refazer o mais cedo possível!
		searchBowKey(event.getCode()).ifPresent(index -> {
			if (pressed) {
				if (index != currentBowingIndex) {
					currentBowingIndex = index;
					bowStartAction.accept(index);
				}
			} else {
				currentBowingIndex = -1;
				bowStopAction.run();
			}
		});
		
		searchNoteKey(event.getCode()).ifPresent(values -> {
			fingerAction.accept(new FingerEvent(values[0], values[1], pressed ? FingerEventType.HOLD : FingerEventType.RELEASE));
		});
	}
	
	private Optional<Integer[]> searchNoteKey(KeyCode key) {
		for (int i = 0; i < noteMatrix.length; i++) {
			for (int j = 0; j < noteMatrix[i].length; j++) {
				if (noteMatrix[i][j] == key) {
					Integer[] values = {i, j};
					return Optional.of(values);
				}
			}
		}
		
		return Optional.empty();
	}

	private Optional<Integer> searchBowKey(KeyCode key) {
		for (int i = 0; i < bowingKeys.length; i++) {
			if (bowingKeys[i] == key) {
				return Optional.of(i);
			}
		}
		
		return Optional.empty();
	}

	public void setFingerAction(Consumer<FingerEvent> fingerAction) {
		this.fingerAction = fingerAction;
	}

	public void setBowStartAction(Consumer<Integer> bowStartAction) {
		this.bowStartAction = bowStartAction;
	}

	public void setBowStopAction(Runnable bowStopAction) {
		this.bowStopAction = bowStopAction;
	}
}
