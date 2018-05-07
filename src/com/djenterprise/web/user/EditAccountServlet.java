package com.djenterprise.web.user;

import com.djenterprise.app.authentication.Registration;
import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.exceptions.UserAlreadyExistsException;
import com.djenterprise.db.user.Login;
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

@WebServlet(name = "EditAccountServlet", urlPatterns = {"/EditAccountServlet"})
@MultipartConfig
public class EditAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String alias = request.getParameter("alias");

            Part filePart = request.getPart("file");
            InputStream inputStream = filePart.getInputStream();

            String errorURL = "";

            UserBO oldUser = UserDAO.getUser((String)request.getSession().getAttribute(Keys.LOGINKEY));

            if( password == null || confirmPassword == null || password.isEmpty() || confirmPassword.isEmpty() ) {

                RequestDispatcher view = getServletContext().getRequestDispatcher("/editUser.jsp?errMsgDef=0");
                view.forward(request, response);

            } else {

                if( ! password.equals("PASSWORD") ) {
                    if( ! Registration.checkPassword(password) ) {
                        if (errorURL.isEmpty()) {
                            errorURL += "?errMsgPass=2";
                        } else {
                            errorURL += "&errMsgPass=2";
                        }
                    }

                    if( ! password.equals(confirmPassword) ) {
                        if( errorURL.isEmpty() ) {
                            errorURL += "?errMsgConfirm=4";
                        } else {
                            errorURL += "&errMsgConfirm=4";
                        }
                    }
                }

                if( ! alias.equals("ALIAS") ) {

                    if( ! Registration.checkAlias(alias) ) {
                        errorURL += "?errMsgAlias=1";
                    }

                    try {
                        Registration.checkAliasAvailability(alias);
                    } catch (UserAlreadyExistsException ex) {
                        if (errorURL.isEmpty()) {
                            errorURL = "?errMsgAliasTaken=11";
                        } else {
                            errorURL += "&errMsgAliasTaken=11";
                        }
                    }
                }

                if( errorURL.isEmpty() ) {

                    UserBO user = new UserBO();
                    user.setUsername((String)request.getSession().getAttribute(Keys.LOGINKEY));
                    user.setAlias(alias);
                    user.setPassword(password);

                    if( ! alias.equals(oldUser.getAlias()) && ! alias.equals("ALIAS") ) {
                        UserDAO.editUserAlias(user);
                    }

                    if( ! password.equals("PASSWORD") ) {
                        UserDAO.editUserPassword(user);
                    }

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
                        UserDAO.editUserAvatar(user);
                    } catch (Exception ex) {
                        user.setInputStream(null);
                    }

                    //Initialize a dispatcher
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                    //Redirect to another page
                    view.forward(request, response);

                } else {
                    response.sendRedirect("editUser.jsp" + errorURL);
                }
            }
        }
    }
}
