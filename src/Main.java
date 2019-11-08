import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("DE.fxml"));
        primaryStage.setTitle("DE Assignment");
        primaryStage.setScene(new Scene(root, 1331, 660));
        primaryStage.show();
    }

// Function which computes given y' with particular x and y
    static double F(double x, double y) {
        return 2 * Math.exp(x) - y;
    }

    public static void main(String[] args) {

        launch(args);
    }
}
