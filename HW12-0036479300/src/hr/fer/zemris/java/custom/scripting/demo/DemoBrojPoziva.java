package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo class the the number of calls smart script
 */
public class DemoBrojPoziva {

    /**
     * Entry point of the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String documentBody = readFromDisk("./examples/brojPoziva.smscr");
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        persistentParameters.put("brojPoziva", "3");
        RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
        // create engine and execute it new SmartScriptEngine(
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(), rc)
                .execute();

        System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
    }

    /**
     * Method used for reading from the disk
     *
     * @param s Path to the file
     * @return String read of the file
     */
    public static String readFromDisk(String s) {

        String read = "";
        try {
            read = new String(
                    Files.readAllBytes(Paths.get(s)),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.err.println("The filepath your entered cannot be resolved to a file");
            System.exit(-1);
        }
        return read;
    }
}
