package br.concatto.violin;

import br.concatto.violin.FingerEvent.FingerEventType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Controller extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViolinScene scene = new ViolinScene();
		Violin violin = new Violin();
		
		scene.onBowingStarted(violin::startBowing);
		scene.onBowingStopped(violin::stopBowing);
		scene.onFingerEvent(event -> {
			if (event.getEventType() == FingerEventType.HOLD) {
				violin.holdFinger(event.getStringIndex(), event.getFingerPosition());
			} else {
				violin.releaseFinger(event.getStringIndex(), event.getFingerPosition());
			}
		});
		
		primaryStage.setTitle("Violin");
		primaryStage.setScene(scene);
		scene.beginListening();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
