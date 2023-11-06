import java.io.*;
import java.util.Properties;

public class Serverconfig {
    private static final String CONFIG_FILE = "server_info.dat";
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 1234;

    private String host;
    private int port;

    public Serverconfig() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            prop.load(input);
            host = prop.getProperty("host", DEFAULT_HOST);
            port = Integer.parseInt(prop.getProperty("port", String.valueOf(DEFAULT_PORT)));
        } catch (IOException ex) {
            host = DEFAULT_HOST;
            port = DEFAULT_PORT;
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
