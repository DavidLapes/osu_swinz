package com.djenterprise.web.user;

import com.djenterprise.app.builder.ProjectBuilder;
import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.user.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/RegistrationServlet"})
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String alias = request.getParameter("alias");

            if( password == null || confirmPassword == null || ! password.equals(confirmPassword) || password.isEmpty() ) {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/registration.jsp");
                view.forward(request, response);
            } else {
                //ProjectBuilder.main(null);


                //Initialize a dispatcher
                RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                //Redirect to another page
                view.forward(request, response);
            }

            /*
            UserBO user = new UserBO();
            user.setUsername(username);
            user.setPassword(password);
            user.setAlias(alias);

            UserDAO.createUser(user);
            */


        }
    }
}
