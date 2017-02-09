package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;


/**
 * Register page servlet implementation. POST and GET http methods are supported. GET performs a redirection
 * to the main JSP page. And the POST method validates given data and records a new user in the database.
 */
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("pwd");
        String firstName = req.getParameter("firstname");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");

        if (nickname == null || password == null || firstName == null || surname == null || email == null
                || nickname.isEmpty() || password.isEmpty() || firstName.isEmpty() || surname.isEmpty() || email.isEmpty()) {
            req.setAttribute("errorMessage", "Sorry, all fields are required!");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        /*form does provide email checking, but in case a user tries to send a post request
        outside the web form(POSTMAN), this should keep the logical integrity of the database*/
        if (Pattern.matches(Util.EMAIL_PATTERN, email)) {
            req.setAttribute("errorMessage", "Your providen a invalid email pattern");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        BlogUser blogUser = DAOProvider.getDAO().getBlogUserByNick(nickname);

        if (blogUser != null) {
            req.setAttribute("errorMessage", "Sorry but the nickname: " + nickname + " is already taken");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        String encodedPassword = Util.calculateHashFromString(password);

        blogUser = new BlogUser();
        blogUser.setNick(nickname);
        blogUser.setEmail(email);
        blogUser.setFirstName(firstName);
        blogUser.setLastName(surname);
        blogUser.setPasswordHash(encodedPassword);

        //store user in database and show success
        DAOProvider.getDAO().addBlogUser(blogUser);
        req.setAttribute("infoMessage", "Successfully added the user with nickname: " + nickname);
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

}
