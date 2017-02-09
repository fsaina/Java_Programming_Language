package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.jsdemo.db.PictureDB;

/**
 * Tag servlet responsible for creating and returning valid tags
 *
 * @author filip
 */
@WebServlet(urlPatterns = {"/servlets/tags"})
public class HtmlTagServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");

        String tags = PictureDB.getTags();
        String buttonTags = buildTag(tags);

        resp.getWriter().write(buttonTags);
        resp.getWriter().flush();
    }

    /**
     * Method makes a button tag string value
     *
     * @param stringTag String tag to be appended
     * @return String of the full html element
     */
    private String buildTag(String stringTag) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] tags = stringTag.split(PictureDB.SPLIT_SIGN);

        for (String tag : tags) {
            stringBuilder.append(
                    "<button class=\"" +
                            "button\" onclick=" +
                            "\"getPic('" + tag.trim() + "')" +
                            "\">" + tag.trim() + "</button>");
        }

        return stringBuilder.toString();
    }
}