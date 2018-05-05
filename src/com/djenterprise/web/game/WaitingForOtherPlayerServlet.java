package com.djenterprise.web.game;

import com.djenterprise.app.game.GameConstruct;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.user.UserDAO;
import com.djenterprise.web.user.Keys;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "WaitingForOtherPlayerServlet", urlPatterns = {"/WaitingForOtherPlayerServlet"})
public class WaitingForOtherPlayerServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {


        }
    }
}
