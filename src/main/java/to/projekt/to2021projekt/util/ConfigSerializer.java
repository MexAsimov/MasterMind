package to.projekt.to2021projekt.util;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigSerializer {
    private String configFilePath = "src/main/resources/config.json";
    private int numOfRounds = 0;
    private int numOfColors = 0;
    private Boolean colorRepetition = null;

    public int getNumOfRounds() throws IOException {
        if(numOfRounds == 0){
            loadConfig();
        }
        return numOfRounds;
    }
    public int getNumOfColors() throws IOException{
        if(numOfColors == 0){
            loadConfig();
        }
        return numOfColors;
    }
    public void updateConfig(int numOfColors, int numOfRounds, boolean colorRepetition) throws IOException {
        this.numOfRounds = numOfRounds;
        this.numOfColors = numOfColors;
        this.colorRepetition = colorRepetition;
        saveConfig();
    }

    private void loadConfig() throws IOException {
        String configText = new String(Files.readAllBytes(Paths.get(configFilePath)), StandardCharsets.UTF_8);
        JSONObject configJO = new JSONObject(configText);
        numOfRounds = (Integer) configJO.get("numOfRounds");
        numOfColors = (Integer) configJO.get("numOfColors");
        colorRepetition = (Boolean) configJO.get("colorRepetition");
    }
    private void saveConfig() throws IOException {
        JSONObject configJO = new JSONObject();
        configJO.put("numOfColors", this.numOfColors);
        configJO.put("numOfRounds", this.numOfRounds);
        configJO.put("colorRepetition", this.colorRepetition);
        FileWriter file = new FileWriter(configFilePath);
        file.write(String.valueOf(configJO));
        file.flush();
    }

    public boolean getColorRepetition() throws IOException {
        if(colorRepetition == null){
            loadConfig();
        }
        return colorRepetition;
    }
}
