package hr.fer.zemris.java.tecaj_13;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index.html")
public class PollsListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<PollEntry> unosi = DAOProvider.getDao().grabAllPolls();
        req.setAttribute("polls", unosi);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

}