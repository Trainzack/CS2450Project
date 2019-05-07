import java.io.File;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class ZoomableScrollPane extends ScrollPane{
	Group zoomGroup;
	Scale scaleTransform;
	Node content;

	public ZoomableScrollPane(Node content)
	{
		this.content = content;
		Group contentGroup = new Group();
		zoomGroup = new Group();
		contentGroup.getChildren().add(zoomGroup);
		zoomGroup.getChildren().add(content);
		setContent(contentGroup);
		scaleTransform = new Scale(1, 1, 0, 0);
		zoomGroup.getTransforms().add(scaleTransform);

	}
}

public class Main extends Application{
	
	private Rectangle currentRectangle;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(1000);
		primaryStage.setHeight(700);
		primaryStage.setTitle("PDF Cropper");

		HBox canvas = new HBox();

		Pane imagePane = new Pane();
		ZoomableScrollPane imageCanvas = new ZoomableScrollPane(imagePane);
		VBox controlBox = new VBox();

		canvas.getChildren().add(controlBox);
		canvas.getChildren().add(imageCanvas);
		Button loadFileButton = new Button("Load file...");
		Button preset1 = new Button("Preset 1");
		Button preset2 = new Button("Preset 2");
		Button preset3 = new Button("Preset 3");
		
		preset1.setLayoutX(800);
		preset1.setLayoutY(100);
		
		preset2.setLayoutX(800);
		preset2.setLayoutY(300);
		
		preset3.setLayoutX(800);
		preset3.setLayoutY(500);
		
		controlBox.getChildren().add(preset1);
		controlBox.getChildren().add(preset2);
		controlBox.getChildren().add(preset3);
		
        
		
		controlBox.getChildren().add(loadFileButton);
		Scene scene = new Scene(canvas);
		
		//fileChooser.showOpenDialog(primaryStage);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		preset1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				currentRectangle = new Rectangle(50,50,400,400);
				currentRectangle.setFill(Color.TRANSPARENT);
				currentRectangle.setStroke(Color.BLACK);
				currentRectangle.setId("RECT");
				imagePane.getChildren().add(currentRectangle);
				
				//primaryStage.show();
			}
			
		});
		
		preset2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				currentRectangle = new Rectangle(50,50,300,400);
				currentRectangle.setFill(Color.TRANSPARENT);
				currentRectangle.setStroke(Color.BLACK);
				imagePane.getChildren().add(currentRectangle);
				
				//primaryStage.show();
			}
			
		});
		
		preset3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				currentRectangle = new Rectangle(50,50,400,200);
				currentRectangle.setFill(Color.TRANSPARENT);
				currentRectangle.setStroke(Color.BLACK);
				imagePane.getChildren().add(currentRectangle);
				
				//primaryStage.show();
			}
			
		});
		
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
				imagePane.getChildren().add(imageView);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
