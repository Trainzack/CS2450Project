import java.io.File;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
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
	protected static final double SCALE_DELTA = 1.0;
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
	
	public void setZoom(double zoom) {
		zoomGroup.setScaleX(zoom);
		zoomGroup.setScaleY(zoom);
	}
}

public class Main extends Application{
	
	private Rectangle currentRectangle;
	private Image currentImage;
	
	private double zoomFactor = 1.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(1000);
		primaryStage.setHeight(700);
		primaryStage.setTitle("PDF Cropper");

		HBox canvas = new HBox();

		Pane imagePane = new Pane();
		ZoomableScrollPane imageCanvas = new ZoomableScrollPane(imagePane);
		VBox controlBox = new VBox();
		controlBox.setMinWidth(100);
		
		canvas.getChildren().add(controlBox);
		canvas.getChildren().add(imageCanvas);
		Button loadFileButton = new Button("Load file...");
		
		
		Button fullPicturePreset = new Button("Full picture");
		controlBox.getChildren().add(fullPicturePreset);
		
		
		fullPicturePreset.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if (currentRectangle != null) {
					imagePane.getChildren().remove(currentRectangle);
				}
				currentRectangle = new Rectangle(50,50,currentImage.getWidth(),currentImage.getHeight());
				currentRectangle.setFill(new Color(0.4,0.4, 1,0.3));
				currentRectangle.setStroke(new Color(0.4,0.4, 1,1));
				currentRectangle.setId("RECT");
				imagePane.getChildren().add(currentRectangle);
				
				
			}
			
		});
		
		
		int[][] presets = {{50, 50, 100, 200}, {60, 60, 500, 390}};
		
		for (int i = 0; i < presets.length; i++) {
			int[] preset = presets[i];
			Button p = new Button("Preset " + (i + 1));
			controlBox.getChildren().add(p);
			p.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (currentRectangle != null) {
						imagePane.getChildren().remove(currentRectangle);
					}
					currentRectangle = new Rectangle(preset[0],preset[1],preset[2],preset[3]);
					currentRectangle.setFill(new Color(0.4,0.4, 1,0.3));
					currentRectangle.setStroke(new Color(0.4,0.4, 1,1));
					currentRectangle.setId("RECT");
					imagePane.getChildren().add(currentRectangle);
					
					//primaryStage.show();
				}
				
			});
		}
		HBox zoomControls = new HBox();

		controlBox.getChildren().add(new Separator());
		
		Button zoomInButton = new Button("+");
		Button zoomOutButton = new Button("-");
		zoomControls.getChildren().add(zoomOutButton);
		zoomControls.getChildren().add(zoomInButton);
		
		zoomInButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				zoomFactor *= 1.33;
				imageCanvas.setZoom(zoomFactor);
				System.out.println(zoomFactor);
			}
			
		});
		

		zoomOutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				zoomFactor /= 1.33;
				imageCanvas.setZoom(zoomFactor);
				System.out.println(zoomFactor);
			}
			
		});
		
		controlBox.getChildren().add(zoomControls);
		
		controlBox.getChildren().add(loadFileButton);
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
				currentImage = image;
				imageView.setLayoutX(50);
				imageView.setLayoutY(50);
				//imageView.setFitHeight(400);
				//imageView.setFitWidth(400);
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
