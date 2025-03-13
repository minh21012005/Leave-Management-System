/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;
import model.LeaveRequest;

/**
 *
 * @author minho
 */
public class ViewMyPagingRequestServlet extends BaseRequiredAuthenticationController{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<LeaveRequest> list = lrd.getMyRequest(employee);
        int totalItems = list.size();
        int pageSize = 2;
        int totalPages = (totalItems/pageSize)+(totalItems%pageSize);
        int currentPage = Integer.parseInt(req.getParameter("currentpage"));
        ArrayList<LeaveRequest> listMain = lrd.getMyRequest(employee, currentPage, pageSize);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        String department = employee.getDepartment();
        req.setAttribute("department", department);
        req.setAttribute("list", listMain);
        req.getRequestDispatcher("/WEB-INF/myRequest.jsp").forward(req, resp);
    }
    
}
