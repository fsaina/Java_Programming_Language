package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Login page servlet implementation. POST and GET http methods are supported. GET performs a redirection to
 * the main JSP page. And the POST method validates given data and records a new user session under
 * current.user.* values. (Those are later invalidated on logout)
 */
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/main.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");

        if (nickname == null || password == null || nickname.isEmpty() || password.isEmpty()) {
            req.setAttribute("message", "Please provide a valid nickname and password" + nickname + password);
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        }

        String calcutatedHashEncoding = Util.calculateHashFromString(password);
        BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nickname);

        if (user == null) {
            //user does not exist
            req.setAttribute("message", "The user with the nickname: " + nickname + " does not exist in the" +
                    "database, please provide a valid nickname");
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        }

        if (!user.getPasswordHash().equals(calcutatedHashEncoding)) {
            //user exists, but the password is wrong
            req.setAttribute("message", "The password for user with nickname: " + nickname + " is not correct, please try" +
                    " again");
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        }

        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());
        //success
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nickname);
    }
}
