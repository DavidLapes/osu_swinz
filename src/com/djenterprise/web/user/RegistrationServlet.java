package com.djenterprise.web.user;

import com.djenterprise.app.authentication.Registration;
import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.exceptions.UserAlreadyExistsException;
import com.djenterprise.db.user.UserDAO;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/RegistrationServlet"})
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    //TODO Check image format and size and resolution

    private void processRequest( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String alias = request.getParameter("alias");

            Part filePart = request.getPart("file");
            InputStream inputStream = filePart.getInputStream();

            String errorURL = "";

            if( password == null || confirmPassword == null || password.isEmpty() || confirmPassword.isEmpty() ) {

                RequestDispatcher view = getServletContext().getRequestDispatcher("/registration.jsp?errMsgDef=0");
                view.forward(request, response);

            } else {

                if( ! Registration.checkAlias(alias) ) {
                    errorURL += "?errMsgAlias=1";
                }

                if( ! Registration.checkPassword(password) ) {
                    if( errorURL.isEmpty() ) {
                        errorURL += "?errMsgPass=2";
                    } else {
                        errorURL += "&errMsgPass=2";
                    }
                }

                if( ! Registration.checkUsername(username) ) {
                    if( errorURL.isEmpty() ) {
                        errorURL += "?errMsgUser=3";
                    } else {
                        errorURL += "&errMsgUser=3";
                    }
                }

                if( ! password.equals(confirmPassword) ) {
                    if( errorURL.isEmpty() ) {
                        errorURL += "?errMsgConfirm=4";
                    } else {
                        errorURL += "&errMsgConfirm=4";
                    }
                }

                try {
                    Registration.checkUsernameAvailability(username);
                } catch (UserAlreadyExistsException ex) {
                    if( errorURL.isEmpty()) {
                        errorURL += "?errMsgUserTaken=33";
                    } else {
                        errorURL += "&errMsgUserTaken=33";
                    }
                }

                try {
                    Registration.checkAliasAvailability(alias);
                } catch (UserAlreadyExistsException ex) {
                    if( errorURL.isEmpty()) {
                        errorURL += "?errMsgAliasTaken=11";
                    } else {
                        errorURL += "&errMsgAliasTaken=11";
                    }
                }

                if( errorURL.isEmpty() ) {

                    UserBO user = new UserBO();

                    user.setUsername(username);
                    user.setPassword(password);
                    user.setAlias(alias);

                    try {
                        BufferedImage bimg = ImageIO.read(inputStream);
                        bimg.toString();
                        int width = bimg.getWidth();
                        int height = bimg.getHeight();
                        if (width != height)
                            errorURL += "?errMsgImgRes=1";
                        if (width > 512) {
                            if( errorURL.isEmpty() ) {
                                errorURL += "?errMsgImgSize=1";
                            } else {
                                errorURL += "&errMsgImgSize=1";
                            }
                        }
                        if (!errorURL.isEmpty()){
                            response.sendRedirect("registration.jsp" + errorURL);
                            return;
                        }

                        user.setInputStream(request.getPart("file").getInputStream());
                    } catch (Exception ex) {
                        user.setInputStream(null);
                    }

                    UserDAO.createUser(user);

                    //Initialize a dispatcher
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/LoginServlet");
                    //Redirect to another page
                    view.forward(request, response);

                } else {
                    response.sendRedirect("registration.jsp" + errorURL);
                }
            }
        }
    }
}
