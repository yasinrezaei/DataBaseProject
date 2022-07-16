import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/login_page.fxml"));
        loader.load();
        primaryStage.setScene(new Scene((Parent) loader.getRoot()));
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.show();






        /* create product
        int id = DataBase.createProduct(new DbProduct("mast"));
        System.out.println("id="+id);

         */
        /* delete product
        DataBase.deleteProduct(new DbProduct(1,"pofak"));

         */






    }

}
