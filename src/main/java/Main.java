import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {


    /**
     * Метод инициализации формы
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Построение графиков функций");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icons/icon.png")));
        primaryStage.show();
    }

    /**
     * Точка входа приложения
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
