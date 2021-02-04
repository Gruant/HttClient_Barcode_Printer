import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpRequestUpload implements HttpHandler {
    private final Path tempPath = Paths.get("../temp").toAbsolutePath();
    private final Path outFile = Paths.get("../temp/out.pdf").toAbsolutePath();
    private final Path cleared = Paths.get("../temp/clearHtml.html").toAbsolutePath();



    public void handle(HttpExchange httpExchange) throws IOException {
        if (!Files.isDirectory(tempPath)) { Files.createDirectory(tempPath); }

        if (Files.isRegularFile(outFile)) {
            Files.delete(outFile);
        }


        if (Files.isRegularFile(cleared)) {
            Files.delete(cleared);
        }

        Files.createFile(outFile);
        Files.createFile(cleared);


        String query = httpExchange.getRequestURI().getQuery();
        query = query.substring(5);

        httpExchange.close();
        try {
            new PrintFile(outFile, cleared, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

