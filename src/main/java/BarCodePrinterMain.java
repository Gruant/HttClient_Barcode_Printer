import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;


public class BarCodePrinterMain {
    private static final int PORT = 8990;
    private static final Logger logger = LoggerFactory.getLogger(BarCodePrinterMain.class);

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();

        server.bind(new InetSocketAddress(PORT), 2);

        server.createContext("/", new HttpRequestUpload());

        server.setExecutor(null);
        server.start();
        logger.info("Server started");
    }

}
