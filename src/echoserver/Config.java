package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private String path;

    public Config(String path) {
        this.path = path;
    }

    public int getPort() {
        Properties config = new Properties();
        try {
            InputStream propStream=this.getClass().getClassLoader().getResourceAsStream(path);
            config.load(propStream);;
            propStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(config.getProperty("Port", "5678"));
    }
}
