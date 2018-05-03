package com.djenterprise.web.user;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.user.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "DisplayAvatarServlet", urlPatterns = {"/DisplayAvatarServlet"})
public class DisplayAvatarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserBO user = UserDAO.getUser((String) request.getSession().getAttribute(Keys.LOGINKEY));
            if ( user.getAvatar() != null ){
                OutputStream output = response.getOutputStream();
                response.setContentType("image/jpg");
                output.write(
                        user.getAvatar().getBytes(1, (int) user.getAvatar().length()));
            } else {
                user = UserDAO.getUser("root_username");
                OutputStream output = response.getOutputStream();
                response.setContentType("image/jpg");
                output.write(
                        user.getAvatar().getBytes(1, (int) user.getAvatar().length())
                );
            }
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
