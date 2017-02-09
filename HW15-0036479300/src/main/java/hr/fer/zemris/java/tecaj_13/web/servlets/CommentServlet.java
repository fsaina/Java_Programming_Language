package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet handler for comment POST requests. Every successful request is added to the database
 */
public class CommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("comment");
        String nickname = (String) req.getSession().getAttribute("current.user.nick");
        String redirectNick = req.getParameter("nickname");
        String entryid = req.getParameter("idEntry");
        Long entryIdNumber;

        if (nickname == null || entryid == null || nickname.isEmpty() || entryid.isEmpty()) {
            req.setAttribute("message", "All fields are required!");
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
            return;
        }

        try {
            entryIdNumber = Long.parseLong(entryid);
        } catch (NumberFormatException e) {
            sendToErrorPage(req, resp, "Please provide a valid id number, you provided: " + entryid);
            return;
        }


        BlogUser blogUser = DAOProvider.getDAO().getBlogUserByNick(nickname);
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryIdNumber);

        DAOProvider.getDAO().addCommentToEntry(entry.getId(), blogUser.getEmail(), text);

        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + redirectNick + "/" + entryIdNumber);
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

}
