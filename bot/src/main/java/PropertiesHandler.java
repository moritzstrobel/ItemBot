import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

abstract class PropertiesHandler {
    public static String getPropertie(String name){
        try (InputStream input = Files.newInputStream(Paths.get("bot/src/main/resources/config.properties"))) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return prop.getProperty(name);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String setPropertie(String name, String value){
        try (
                OutputStream output = Files.newOutputStream(Paths.get("bot/src/main/resources/config.properties"));
                InputStream input = Files.newInputStream(Paths.get("bot/src/main/resources/config.properties"))
            ) {
            Properties prop = new Properties();
            prop.load(input);
            prop.setProperty(name, value);
            prop.store(output, value);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
