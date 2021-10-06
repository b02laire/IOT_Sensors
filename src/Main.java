import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.event.*;

 public class Main extends Application{
   public void start(Stage primaryStage)
   {
   try {
       BorderPane root = new BorderPane();
       root.setTop(createToolbar());
       root.setBottom(createStatusbar());
       root.setCenter(createMainContent());
      
       Scene scene = new Scene (root, 800, 400);
       scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
       primaryStage.setScene(scene);
       primaryStage.setTitle("Demo JavaFX");
       primaryStage.show();
       }
   catch(Exception e) {e.printStackTrace();}
   }

 private Node createToolbar()
   {
   ToolBar toolbar = new ToolBar(new Button("New"), new Button ("Open"),
   new Separator(), new Button("Clean"));
   return toolbar;
   }

 private Node createStatusbar()
   {
   HBox statusbar = new HBox();
   statusbar.getChildren().addAll(new Label("Name:"),
     new TextField("    "));
     return statusbar;
   }

 private Node createMainContent()
   {
   Group g = new Group();
   
   Text text = new Text ("Centrale \n Cergy");
    text.setTextAlignment(TextAlignment.JUSTIFY);
    text.setFont(Font.font ("Times New Roman",
    FontWeight.BOLD, FontPosture.REGULAR, 40));
    text.setFill(Color.CYAN);
    text.setStrokeWidth(2);
    text.setStroke(Color.BLACK);
    text.setUnderline(true);
   text.setX(50);
   text.setY(150);
   
  Image image = new Image("file:///home/b02laire/Documents/ENSEA/Java/javafx_eve/src/bananacover.jpg");
  ImageView imageView = new ImageView(image);
  imageView.setX(500);
  imageView.setY(70);
  imageView.setFitHeight(300);
  imageView.setFitWidth(600);
  imageView.setOnMouseClicked(
		  new EventHandler<MouseEvent>()
		  {
			  public void handle(MouseEvent e) 
			  {
				  Alert alert = new Alert(AlertType.INFORMATION);
				  alert.setTitle("Ape Alert");
				  alert.setHeaderText("Look, an Information Dialog");
				  alert.setContentText("Seriously man, where banana ?");
				  alert.showAndWait().ifPresent(rs -> {
				      if (rs == ButtonType.OK) {
				          System.out.println("Pressed OK.");
				      }
				  });
			  }
		  });
  
   g.getChildren().add(imageView);
   return g;
   }
}
