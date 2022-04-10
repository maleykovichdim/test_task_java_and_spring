package com.example.test_java_task.service;

import com.example.test_java_task.common.SectorStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HtmlService {

    private final SectorStorage sectorStorage;

    private final String INDEX_HTML_START =
            "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "  <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "  <link rel=\"stylesheet\" href=\"https://bootswatch.com/4/cosmo/bootstrap.min.css\">\n" +
                    "  <link href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\" rel=\"stylesheet\" integrity=\"sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN\" crossorigin=\"anonymous\">\n" +
                    "  <title>Test task</title>\n" +
                    "</head>" +
                    "<body>" +
                    "    <div id=\"main-div\" >\n" +
                    "    <div id=\"main-div2\">" +
                    "      <h1>Please enter your name and pick the Sectors you are currently involved in.</h1>\n" +
                    "      <br>\n" +
                    "      <br>Name:\n" +
                    "      <div class=\"form-group\">\n" +
                    "        <input type=\"text\" id=\"name\" class=\"form-control\"" +
                    "        maxlength=\"32\" name=\"first_name\" " +
                    "        placeholder=\"your Name\" required pattern=\"[A-Za-z]{1,32}\">\n" +
                    "      </div>\n" +
                    "      <br>\n" +
                    "      <br>\n" +
                    "      <div>" +
                    "      Sectors:\n" +
                    "      <select id=\"select\"  multiple=\"\" size=\"5\">";

    private final String INDEX_HTML_END = "</select>\n" +
            "    </div>" +
            "    <br>\n" +
            "    <br>\n" +
            "    <div><label><input id=\"checkbox\" type=\"checkbox\"> Agree to terms</label></div>"+
            "    <br>\n" +
            "    <br>\n" +
            "    <div><label id=\"mode\">Mode: ADD NEW user</label></div>\n" +
            "    <input id=\"button_save\" type=\"submit\" value=\"Save\">" +
            "    <input id=\"button_change_mode\"  type=\"submit\" value=\"Add New / Edit Mode\">" +
            "    <input id=\"button_load_mode\"  type=\"submit\" value=\"Load\">\n" +
            "    </div>" +
            "    </div>" +
            "  <script src=\"app.js\"></script>\n" +
            "</body></html>";


    public HtmlService(SectorStorage sectorStorage) {
        this.sectorStorage = sectorStorage;
    }

    public String getIndexHtml(){
        StringBuilder str = new StringBuilder();
        str.append(INDEX_HTML_START);
        str.append(sectorStorage.getOptionalsHtml());
        str.append(INDEX_HTML_END);
        return str.toString();
    }
}
