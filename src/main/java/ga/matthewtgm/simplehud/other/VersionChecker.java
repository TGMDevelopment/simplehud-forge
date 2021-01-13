package ga.matthewtgm.simplehud.other;

import ga.matthewtgm.simplehud.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {

    private String version;
    private String download_url;
    private boolean emergency;

    private BufferedReader reader;
    public String verJSON;
    public JSONObject verOBJ;

    public Logger logger = LogManager.getLogger(Constants.NAME + " (" + this.getClass().getSimpleName() + ")");

    {
        try {
            reader = new BufferedReader(new InputStreamReader(new URL("https://dl.dropboxusercontent.com/s/2s4vdn26af04q6w/version.json").openStream()));
            verJSON = reader.readLine();
            verOBJ = (JSONObject) new JSONParser().parse(verJSON);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(verJSON);
            version = String.valueOf(obj.get("latest"));
            return version;
        } catch(Exception e) {
            e.printStackTrace();
            return new String();
        }
    }

    public String getDownloadURL() {
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(verJSON);
            download_url = String.valueOf(obj.get("download"));
            return download_url;
        } catch(Exception e) {
            e.printStackTrace();
            return new String();
        }
    }

    public boolean getEmergencyStatus() {

        try {

            JSONObject obj = (JSONObject) new JSONParser().parse(verJSON);

            if(isNull(obj.get("emergency_update_" + Constants.VER)))
                emergency = false;
            else emergency = (boolean) obj.get("emergency_update_" + Constants.VER);

            return emergency;

        } catch(Exception e) {

            e.printStackTrace();
            return false;

        }

    }

    private boolean isNull(Object o) {
        return o == null;
    }

}