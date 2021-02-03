import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import java.io.*;
import java.nio.file.Path;


public class PrintFile {
    private final Path out;
    private final Path cleared;
    private final String html;


    public PrintFile(Path out, Path cleared, String html) throws IOException {
        this.out = out;
        this.cleared = cleared;
        this.html = html;
        print();
    }

    private void clearHtml() throws IOException {
        File outFile = new File(cleared.toString());
        try (BufferedWriter out = new BufferedWriter(new FileWriter(outFile))) {
            out.write(html);
        }
    }


    public void createPdf() throws IOException {
        ConverterProperties properties = new ConverterProperties();
        PdfWriter writer = new PdfWriter(out.toString());
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(new PageSize(164, 190).rotate());
        Document document = HtmlConverter.convertToDocument(
                html, pdf, properties);
        document.getRenderer().close();
        PdfPage page = pdf.getPage(1);

        page.setMediaBox(new Rectangle(30, 30, 114, 120));
        document.close();

    }

    public void print() throws IOException {
        clearHtml();
        createPdf();
        String str = "lp -o orientation-requested=3 " +
                out.toAbsolutePath();
        Runtime.getRuntime().exec(str);
    }
}
