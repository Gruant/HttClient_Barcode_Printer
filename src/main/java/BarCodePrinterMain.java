import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


public class BarCodePrinterMain {
    private static final int PORT = 8990;

    public static void main(String[] args) throws IOException {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(PORT), 0);

            server.createContext("/", new HttpRequestUpload());

            server.setExecutor(null);
            server.start();
    }
}
