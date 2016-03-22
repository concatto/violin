package br.concatto.violin;

import java.util.function.Consumer;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ViolinScene extends Scene {
	private Pane root;
	private VBox stringContainer = new VBox(5);
	private KeyListener keyListener = new KeyListener();
	
	public ViolinScene() {
		super(new VBox(5), 200, 200);
		this.root = (Pane) getRoot();
		
		stringContainer.setOnKeyPressed(keyListener);
		stringContainer.setOnKeyReleased(keyListener);
		
		root.getChildren().add(stringContainer);
	}
	
	public void beginListening() {
		getWindow().focusedProperty().addListener((obs, o, n) -> {
			if (n) stringContainer.requestFocus();
		});
	}

	public void onFingerEvent(Consumer<FingerEvent> fingerAction) {
		keyListener.setFingerAction(fingerAction);
	}

	public void onBowingStarted(Consumer<Integer> bowStartAction) {
		keyListener.setBowStartAction(bowStartAction);
	}

	public void onBowingStopped(Runnable bowStopAction) {
		keyListener.setBowStopAction(bowStopAction);
	}
}
