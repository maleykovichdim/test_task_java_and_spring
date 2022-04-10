
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Main {


    public static  void main(String[] args) throws Exception {

    final String WIKI = "http://127.0.0.1:5500/index-1.html";
    final String FILE_PATH =    "c:/work/output.json";

        Document jsoupDoc;
        try {
            jsoupDoc = Jsoup
                    .connect(WIKI)
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36")
                    .get();
        } catch (HttpStatusException ex) {
            System.out.println("error JSOUP get");
            return;
        }

        Elements options = jsoupDoc.select("option");
        String[] previousRelatedIds = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};// save anсestor value

       int previousSpaces = 0;

        for (Element op: options){
            int spaces = 0;
            while (true){
                if ((int)op.wholeText().charAt(spaces) == 160){
                    spaces++;
                }
                else
                {
                    break;
                }
            }


           String text = op.text();
            if (text.contains("&")){
                text = text.replace("&","&amp;");
            }

            System.out.println("<insert tableName=\"sectors\">");
            System.out.println("    <column name=\"name\">"+text+"</column>");
            System.out.println("    <column name=\"value\">"+ op.attr("value") +"</column>");

            if (spaces == 0) {
                System.out.println("    <column name=\"related_id\">0</column>");
            }else {
                System.out.println(
                    "    <column name=\"related_id\">"+previousRelatedIds[spaces-4] +"</column>");
            }
            previousRelatedIds[spaces] = op.attr("value");
            System.out.println("    <column name=\"depth\">"+spaces+"</column>");
            System.out.println("</insert>");

        }
    }

}
