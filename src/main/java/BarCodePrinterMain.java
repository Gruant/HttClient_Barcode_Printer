import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class BarCodePrinterMain {
    private static final int PORT = 8990;
    private static final Logger logger = LoggerFactory.getLogger(BarCodePrinterMain.class);
    private static boolean isStopped = false;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
        if (args != null && args.length > 0){
            if(args[0].equals("start")){
                server.bind(new InetSocketAddress(PORT), 2);

                server.createContext("/", new HttpRequestUpload());

                server.setExecutor(null);
                server.start();
                logger.info("Server started");
                Scanner scanner = new Scanner(System.in);
                while (!isStopped) {
                    try {
                        String command = scanner.next();
                        if (command.equals("stop")) {
                            server.stop(0);
                            logger.info("Server is stopped");
                            isStopped = true;
                        } else {
                            System.out.println("For stop server use command 'stop'");
                        }
                    } catch (Exception exception) {
                        logger.error(String.valueOf(exception));
                    }
                }

            } else if(args[0].equals("stop")) {
                server.stop(0);
                logger.info("Server is stopped");
            } else {
                logger.error("Try to start without arg START");
                System.out.println("To start BarCodePrinter use in args: start");
                server.stop(0);
            }
        } else {
            logger.error("Try to start without arg START");
            System.out.println("To start BarCodePrinter use in args: start");
            server.stop(0);
        }
    }

}
