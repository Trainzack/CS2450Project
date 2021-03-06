import java.io.File;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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
	
	protected static final int CONTROL_WIDTH = 120;
	
	private Rectangle currentRectangle;
	private ImageView currentImage;
	
	private double zoomFactor = 1.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(1000);
		primaryStage.setHeight(700);
		primaryStage.setTitle("PDF Cropper");

		HBox canvas = new HBox();
		canvas.setPadding(new Insets(5));

		Pane imagePane = new Pane();
		ZoomableScrollPane imageCanvas = new ZoomableScrollPane(imagePane);
		VBox controlBox = new VBox();
		controlBox.setSpacing(10);
		controlBox.setMinWidth(CONTROL_WIDTH + 15);
		
		canvas.getChildren().add(controlBox);
		canvas.getChildren().add(imageCanvas);
		Button loadFileButton = new Button("Load file...");
		

		HBox zoomControls = new HBox();
		
		Button zoomInButton = new Button("+");
		Button zoomOutButton = new Button("-");
		zoomControls.setSpacing(10);
		zoomControls.getChildren().add(zoomOutButton);
		zoomControls.getChildren().add(new Label("Zoom"));
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
	

		loadFileButton.setMinWidth(CONTROL_WIDTH);
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
				if (currentImage != null) {
					imagePane.getChildren().remove(currentImage);
				}
				currentImage = imageView;
				imageView.setLayoutX(50);
				imageView.setLayoutY(50);
				//imageView.setFitHeight(400);
				//imageView.setFitWidth(400);
				imagePane.getChildren().add(imageView);
				//primaryStage.setScene(scene);
				//primaryStage.show();
			}
			
		});
		

		VBox presetsPane = new VBox();
		presetsPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
		            BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
		presetsPane.setSpacing(2);
		presetsPane.setPadding(new Insets(5));
		
		controlBox.getChildren().add(new Separator());
		controlBox.getChildren().add(presetsPane);
		Label presetsLabel = new Label("Presets");
		presetsPane.getChildren().add(presetsLabel);
		
		Button fullPicturePreset = new Button("Full picture");
		presetsPane.getChildren().add(fullPicturePreset);
		fullPicturePreset.setMaxWidth(CONTROL_WIDTH);
		
		fullPicturePreset.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if (currentRectangle != null) {
					imagePane.getChildren().remove(currentRectangle);
				}
				if (currentImage == null) {
					return;
				}
				currentRectangle = new Rectangle(50,50,currentImage.getImage().getWidth(),currentImage.getImage().getHeight());
				currentRectangle.setFill(new Color(0.4,0.4, 1,0.3));
				currentRectangle.setStroke(new Color(0.4,0.4, 1,1));
				currentRectangle.setId("RECT");
				imagePane.getChildren().add(currentRectangle);
				
				
			}
			
		});
		
		int[][] presets = {{150, 200, 700, 550}, {60, 60, 500, 390}, {50, 50, 200, 400}, {50, 50, 100, 400}, {50,50,300,500}, {50,50,500,700}};
		String[] presetNames = {"Custom 1", "Custom 2", "4 x 3", "2 x 3", "5 x 7", "8.5 x 11"};

		for (int i = 0; i < presets.length; i++) {
			int[] preset = presets[i];
			Button p = new Button(presetNames[i]);
			p.setMinWidth(CONTROL_WIDTH);
			presetsPane.getChildren().add(p);
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
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
