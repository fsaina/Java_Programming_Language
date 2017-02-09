package hr.fer.zemris.java.tecaj_13.web.servlets.entry;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Event handler for the HTML post event of adding a new entry from a user to the database. POST method
 * expects title, text and id number values to be inputted.
 */
public class EntryHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String idNumber = req.getParameter("id");

        if (title == null || text == null || title.isEmpty() || text.isEmpty()) {
            req.setAttribute("message", "Sorry, please enter a valid text and title");
            req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
            return;
        }

        String nickname = (String) req.getSession().getAttribute("current.user.nick");

        if (nickname == null) {
            req.setAttribute("message", "Sorry, you have to be logged in to post new entries");
            req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
            return;
        }

        BlogEntry entry;
        if (idNumber == null || idNumber.isEmpty()) {
            //id number is unknown, create new
            entry = new BlogEntry();
            entry.setCreatedAt(new Date());
            entry.setLastModifiedAt(new Date());
            entry.setText(text);
            entry.setTitle(title);

            BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nickname);
            DAOProvider.getDAO().addBlogEntryToUser(entry, user);
        } else {
            //edit a existing entry
            entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(idNumber));
            entry.setText(text);
            entry.setTitle(title);
            entry.setLastModifiedAt(new Date());
        }

        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nickname);
    }
}
