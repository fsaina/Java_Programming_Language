package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation that handles all traffic to /servlet/author* path. Request path is parsed and given
 * to a valid method implementation for hadling
 */
public class AuthorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getPathInfo();
        String path = req.getServletPath();
        Long idLoggedIn = (Long) req.getSession().getAttribute("current.user.id");

        String nickname = null;
        String option = null;


        String[] split = info.split("\\/");

        if (split.length == 2) {
            nickname = split[1];
        } else if (split.length >= 3) {
            option = split[2];
            nickname = split[1];

            //create/edit a blog entry
            if (option.toLowerCase().equals("new")) {
                if (idLoggedIn == null) {
                    sendToErrorPage(req, resp, "Only registered users can add new entries");
                    return;
                }
                req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
                return;
            } else if (option.toLowerCase().equals("edit")) {
                if (idLoggedIn == null) {
                    sendToErrorPage(req, resp, "Only registered users can edit entries");
                    return;
                }
                requestEditOperation(req, resp, split[3], nickname);
                return;
            } else {
                if (requestEntryIdByUser(req, resp, nickname, option))
                    return;
            }
        }

        BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nickname);
        if (user == null) {
            sendToErrorPage(req, resp, "User " + nickname + " is not an author");
            return;
        }

        if (nickname != null) {
            List<BlogEntry> blogEntrys = DAOProvider.getDAO().getEntriesMadeByUserWithNick(nickname);
            if (blogEntrys != null) {
                req.setAttribute("blogEntrys", blogEntrys);
                req.setAttribute("pathData", path);
            }
        }

        req.setAttribute("testData", info);
        req.setAttribute("nickname", nickname);
        req.getRequestDispatcher("/WEB-INF/pages/BlogList.jsp").forward(req, resp);
    }

    /**
     * Method dispatches given error message value to the JSP error page
     *
     * @param req  HttpServletRequest request object to forward attributes to
     * @param resp HttpServletResponse
     * @param text String text to be shown to the user
     * @throws ServletException If a internet error occurred
     * @throws IOException      In case of reading exception
     */
    private void sendToErrorPage(HttpServletRequest req, HttpServletResponse resp, String text) throws ServletException, IOException {
        req.setAttribute("message", text);
        req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
    }

    /**
     * Method implementation handles entry by user operation performing. Provided values are tested and
     * submitted to the BlogEntry JSP page for later user interaction
     *
     * @param req      HttpServletRequest request object to forward attributes to
     * @param resp     HttpServletResponse
     * @param nickname String nickname of the current user
     * @throws ServletException If a internet error occurred
     * @throws IOException      In case of reading exception
     */
    private boolean requestEntryIdByUser(HttpServletRequest req, HttpServletResponse resp, String nickname, String option) throws ServletException, IOException {
        //servlet/author/NICK/EID
        String eid = option;
        Long eidNumber;

        try {
            eidNumber = Long.parseLong(eid);
        } catch (NumberFormatException e) {
            sendToErrorPage(req, resp, "The EID number your provided is not valid");
            return true;
        }

        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eidNumber);
        boolean contains = DAOProvider.getDAO().getBlogUserByNick(nickname).getEntries().contains(entry);

        if (!contains) {
            sendToErrorPage(req, resp, "There is no entry by the EID: " + eidNumber + " for the requested user");
            return true;
        }

        req.setAttribute("blogEntry", entry);
        req.setAttribute("nickname", nickname);
        req.getRequestDispatcher("/WEB-INF/pages/BlogEntryDetailed.jsp").forward(req, resp);
        return false;
    }

    /**
     * Method implementation handles edit operation performing. Provided values are tested and submitted to
     * the BlogEntry JSP page for later user interaction
     *
     * @param req      HttpServletRequest request object to forward attributes to
     * @param resp     HttpServletResponse
     * @param id       String blog entry number
     * @param nickname String nickname of the current user
     * @throws ServletException If a internet error occurred
     * @throws IOException      In case of reading exception
     */
    private void requestEditOperation(HttpServletRequest req, HttpServletResponse resp, String id, String nickname) throws ServletException, IOException {
        Long idNumber;
        try {
            idNumber = Long.parseLong(id);
        } catch (NumberFormatException e) {
            sendToErrorPage(req, resp, "You provided a invalid blog entry number format: " + id);
            return;
        }
        BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nickname);
        boolean contains = false;

        BlogEntry entry = null;

        for (BlogEntry entry2 : user.getEntries()) {
            if (entry2.getId().equals(idNumber)) {
                contains = true;
                entry = entry2;
                break;
            }
        }

        if (!contains) {
            sendToErrorPage(req, resp, "The user " + nickname + " has no entries with id number: " + idNumber);
            return;
        }

        req.setAttribute("title", entry.getTitle());
        req.setAttribute("text", entry.getText());
        req.setAttribute("idNumber", idNumber);

        req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
    }
}
