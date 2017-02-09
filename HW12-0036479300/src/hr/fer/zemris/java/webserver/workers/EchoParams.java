package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.Set;


/**
 * Echo worker class abstracting a web worker implementation returning a http page all the given parameters in
 * a http table
 */
public class EchoParams implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) {

        context.setStatusCode(200);
        context.setMimeType("text/html");
        Set<String> names = context.getParameterNames();
        try {
            context.write("<html><body>");
            context.write("<h1>Echo parameters:</h1>");
            if (names.isEmpty()) {
                context.write("<p>You did not provide any parameters :/</p>");
            } else {
                context.write("<table style=\"width=100%\">");

                for (String paramName : names) {
                    String value = context.getParameter(paramName);

                    context.write("<tr>");
                    context.write("<td>" + paramName + "</td>");
                    context.write("<td>" + value + "</td>");
                    context.write("</tr>");
                }

                context.write("</table>");
            }
            context.write("</body></html>");
        } catch (IOException ex) {
            System.err.println("Error processing a echo params request!");
        }
    }
}