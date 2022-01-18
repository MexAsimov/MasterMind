package to.projekt.to2021projekt.FXutils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import to.projekt.to2021projekt.HelloApplication;

import java.io.IOException;

public class SceneProvider {
    private Scene scene;

    private static SceneProvider instance;
    private static FXMLLoader loader;

    private SceneProvider() {

    }

    public static SceneProvider getInstance() {
        if (instance == null) {
            instance = new SceneProvider();
        }
        return instance;
    }

    public Scene getScene() {
        return scene;
    }

    public void loadScene(String path, String cssPath){
        try {
            loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource(path));
            this.scene = new Scene(loader.load());
            if(cssPath != null){
                this.scene.getStylesheets().add(cssPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FXMLLoader getLoader(){
        return this.loader;
    }
}
