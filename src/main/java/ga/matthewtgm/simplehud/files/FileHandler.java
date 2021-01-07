package ga.matthewtgm.simplehud.files;

import ga.matthewtgm.simplehud.Constants;
import net.minecraft.client.Minecraft;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    public File matthewtgmModsDir = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/config", "MatthewTGM's Mods");
    public File modDir = new File(matthewtgmModsDir, Constants.NAME);
    public File elementDir = new File(modDir, "HUD Elements");
    public File otherDir = new File(modDir, "Other");
    public List<File> directories = Arrays.asList(matthewtgmModsDir, modDir, elementDir, otherDir);

    public void init() {
        this.directories.forEach(d -> {
            if(!d.exists()) d.mkdirs();
        });
    }

    public void save(String name, File directory, JSONObject content) {
        BufferedWriter writer = null;
        try {
            if(!directory.exists()) {
                directory.mkdirs();
                Thread.sleep(1000);
            }
            File file = new File(directory, name + ".json");
            if(!file.exists()) file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toJSONString());
        } catch(Exception e) {
            e.printStackTrace();
            try {
                writer.flush();
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public JSONObject load(String name, File directory) {
        try {
            if(!directory.exists()) {
                directory.mkdirs();
                Thread.sleep(1000);
            }
            File file = new File(directory, name + ".json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            reader.lines().forEach(builder::append);
            return (JSONObject) new JSONParser().parse(builder.toString());
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}