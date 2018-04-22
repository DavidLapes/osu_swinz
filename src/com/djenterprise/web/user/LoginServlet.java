package com.djenterprise.web.user;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.user.Login;
import com.djenterprise.db.user.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    public static final String LOGINKEY = "__DJLOGIN__";
    public static final String ALIASKEY = "__DJALIAS__";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username.isEmpty()|| password.isEmpty()){
                response.sendRedirect("index.jsp?field=0");
            } else {
                UserBO user = new UserBO();
                user.setUsername(username);
                user.setPassword(password);
                if (Login.testLogin(user)) {
                    session.setAttribute(LOGINKEY, user.getUsername());
                    session.setAttribute(ALIASKEY, UserDAO.getUser(username).getAlias());
                }
                response.sendRedirect("index.jsp");
            }
        }
    }
}
