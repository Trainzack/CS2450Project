import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;




public class Main extends Application{
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(1000);
		primaryStage.setHeight(700);
		primaryStage.setTitle("PDF Cropper");
		
		Pane canvas = new Pane();
		Button loadFileButton = new Button("Load file...");
		canvas.getChildren().add(loadFileButton);
		Scene scene = new Scene(canvas);
		
		//fileChooser.showOpenDialog(primaryStage);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		loadFileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Image files", "*.png","*.jpg","*.gif");
				fileChooser.getExtensionFilters().add(extension);
				fileChooser.setTitle("Open PDF");
				File file = fileChooser.showOpenDialog(primaryStage);
				Image image = new Image(file.toURI().toString());
				ImageView imageView = new ImageView(image);
				imageView.setLayoutX(50);
				imageView.setLayoutY(50);
				imageView.setFitHeight(400);
				imageView.setFitWidth(400);
				canvas.getChildren().add(imageView);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
