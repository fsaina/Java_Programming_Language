package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.jsdemo.db.PictureDB;
import hr.fer.zemris.jsdemo.db.Picture;

/**
 * Image servlet responsible for returning the images at a given location
 *
 * @author filip
 */
@WebServlet(urlPatterns = {"/servlets/image"})
public class ImageServlet extends HttpServlet {

	/**
	 * reference httpservlet req
	 */
	private HttpServletRequest request = null;

	/**
	 * Position of images
	 */
	public static final String IMAGELOCATION = "images";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imageName = req.getParameter("img");
		resp.setContentType("text/html;charset=UTF-8");
		request = req;

		htmlCodeWriter(resp, imageName);

		resp.getWriter().flush();
	}

	/**
	 * Method constructs the html from scratch with the required image object
	 *
	 * @param resp  HttpServletResponse object abstracting the response
	 * @param image image name string link
	 * @throws IOException Thrown when not valid image name provided
	 */
	private void htmlCodeWriter(HttpServletResponse resp, String image)
			throws IOException {
		PrintWriter writer = resp.getWriter();
		Picture picture = PictureDB.provideByName(image);

		ServletContext context = request.getServletContext();
		String fullPath = context.getContextPath();
		fullPath = fullPath.replace("servlets", "");

		writer.write("<html><head>");
		writer.write("<title>Informations about " + picture.getName() + "</title>");
		writer.write("</head><body>");
		writer.write("<div class=\"img\">");
		writer.write("<img src=\""+ fullPath+ "/" + ""+IMAGELOCATION+"/" + image + "\"/>");
		writer.write("<p><b>Description: </b>" + picture.getDescription() + "</p>");
		writer.write("<p><b>Tags: </b>" + picture.getTags() + "</p>");
		writer.write("</div>");
		writer.write("</body></html>");
	}
}
