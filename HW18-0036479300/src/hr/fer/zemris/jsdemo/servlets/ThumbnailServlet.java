package hr.fer.zemris.jsdemo.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.jsdemo.db.PictureDB;
import hr.fer.zemris.jsdemo.db.Picture;

/**
 * Thumbnail image servlet class implementation.
 * Returns thumbnails instead full images for better UX.
 *
 * @author filip
 */
@WebServlet(urlPatterns = {"servlets/thumbnails"})
public class ThumbnailServlet extends HttpServlet {

    /**
     * Path to the project
     */
    private String webInfPath;

    /**
     * Reference to the request
     */
    private HttpServletRequest request;

    /**
     * Height of the thumbnail
     */
    private static final int HEIGHT = 220;

    /**
     * Width of the project
     */
    private static final int WIDTH = 350;

    /**
     * Position of thumbnails
     */
    private static final String THUMBNAILLOCATION = "thumbnails";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        request = req;
        webInfPath = request.getServletContext().getRealPath("/");

        String tagName = req.getParameter("tag");
        List<Picture> pictures = PictureDB.getPicturesByTag(tagName);
        String thumbnails = makeThumbnails(pictures, req);
        resp.getWriter().write(thumbnails);

        resp.getWriter().flush();
    }

    /**
     * Returns if the given picture is thumbnail
     *
     * @param picture Picture object to be checked
     * @return true if is, false otherwise
     */
    private boolean isThumbnail(Picture picture) {
        // File file = new File(webInfPath + "/thumbnails");
        File file = new File(webInfPath + "/" + THUMBNAILLOCATION);

        if (file == null || !file.exists()) {
            file.mkdir();
        }

        File[] pictures = file.listFiles();
        for (File p : pictures) {
            if (p.isDirectory()) {
                continue;
            }
            if (p.getName().trim().equals(picture.getName().trim())) {
                return true;
            }
        }

        return false;
    }


    /**
     * Returns the servlet link of the given picture
     *
     * @param picture Picture object to be returned
     * @return String html notation link
     */
    private String getServletLink(Picture picture) {
        return "servlets/image?img=" + picture.getName().trim();
    }


    /**
     * Method converts picture into thumbnail
     *
     * @param picture Picture model object to be converted
     */
    private void turnPictureIntoThumbnail(Picture picture) {
        try {
            Files.copy(Paths.get(webInfPath + "/" + ImageServlet.IMAGELOCATION + "/" +
                            picture.getName()),
                    Paths.get(webInfPath + "/" + THUMBNAILLOCATION + "/" +
                            picture.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error turning pictures into thumbnail");
        }
    }


    /**
     * Method returns the image link provided
     *
     * @param picture Picture object given
     * @param request request object
     * @return String of the thumbnail path
     */
    private String getImageLink(Picture picture, HttpServletRequest request) {
        return "thumbnails/" + picture.getName();
    }


    /**
     * Method creates Thumbnail string from given data
     *
     * @param pictures list of pictures to be converted
     * @param request  request object
     * @return String in html notation making the picture thumbnails
     */
    private String makeThumbnails(List<Picture> pictures, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();

        for (Picture picture : pictures) {
            if (!isThumbnail(picture)) {
                turnPictureIntoThumbnail(picture);
            }
            sb.append(buildImageHtmlString(picture, request));
        }

        return sb.toString();
    }


    /**
     * Method builds the html string from given data - keeping the WIDTH and HEIGHT
     * construct provided on top
     *
     * @param picture Picture image object to represent
     * @param request HttpServletRequest request object
     * @return String of the full html element
     */
    private String buildImageHtmlString(Picture picture, HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<div id=\"image_box\">");
        stringBuilder.append("<a href=\"" +
                getServletLink(picture) + "\">");
        stringBuilder.append("<img class=\"image\" src=\"" +
                getImageLink(picture, request) + "\" ");
        stringBuilder.append("alt=\"" + picture.getName() + "\" " +
                "height=\"" + HEIGHT + "\" width=\"" + WIDTH + "\">");
        stringBuilder.append("</a></div>");
        return stringBuilder.toString();
    }


}
