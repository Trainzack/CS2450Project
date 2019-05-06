import java.io.File;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
//import javafx.event.ActionEvent; 


public class Main extends Application{

	
	public static void main(String[] args) {
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage) throws Exception {
		
				
		Button chooseButton = new Button("Open a file");
		chooseButton.setOnAction(event ->{
			FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Image files", "*.png","*.jpg","*.gif");
			fileChooser.getExtensionFilters().add(extension);
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			if(selectedFile != null) {
				Image image=new Image(selectedFile.toURI().toString()); 
				ImageView imageview=new ImageView(image);
				imageview.setPreserveRatio(true); 
								
				HBox hbox = new HBox(10,imageview);
				hbox.setAlignment(Pos.CENTER);
								
				Scene scene = new Scene(hbox);
				primaryStage.setWidth(1024);
				primaryStage.setHeight(768);
				primaryStage.setScene(scene);
				primaryStage.show();
		        
			}
			
			
			
			
		});
		HBox hbox = new HBox(chooseButton);
		hbox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(hbox);
		primaryStage.setWidth(1024);
		primaryStage.setHeight(768);
		primaryStage.setTitle("PDF Cropper");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}
