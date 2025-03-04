/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Employee;

/**
 *
 * @author minho
 */
public class DeleteServlet extends BaseRequiredAuthenticationController{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        int requestid = Integer.parseInt(req.getParameter("requestid"));
        LeaveRequestDao lrd = new LeaveRequestDao();
        lrd.delete(requestid);
        String message = "Successful!";
        HttpSession session = req.getSession();
        session.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
    
}
