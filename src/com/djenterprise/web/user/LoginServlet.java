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
            if (username.isEmpty()|| password.isEmpty() || username.equals("USERNAME") || password.equals("PASSWORD") ){
                response.sendRedirect("login.jsp?errMsg=1");
            } else {
                UserBO user = new UserBO();
                user.setUsername(username);
                user.setPassword(password);
                if (Login.testLogin(user)) {
                    session.setAttribute(Keys.LOGINKEY, user.getUsername());
                    session.setAttribute(Keys.ALIASKEY, UserDAO.getUser(username).getAlias());
                    response.sendRedirect("index.jsp");
                } else {
                    session.setAttribute(Keys.LOGINFAILKEY,username);
                    response.sendRedirect("login.jsp?errMsg=2");
                }
            }
        }
    }
}
