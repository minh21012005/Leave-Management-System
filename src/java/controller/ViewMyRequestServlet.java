package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;
import model.LeaveRequest;

public class ViewMyRequestServlet extends BaseRequiredAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<LeaveRequest> list = lrd.getMyRequest(employee);
        int totalItems = list.size();
        int pageSize = 5;
        int totalPages = (totalItems + pageSize - 1) / pageSize;
        int currentPage = Integer.parseInt(req.getParameter("currentpage"));
        ArrayList<LeaveRequest> listMain = lrd.getMyRequest(employee, currentPage, pageSize);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        String department = employee.getDepartment();
        req.setAttribute("department", department);
        req.setAttribute("list", listMain);
        req.getRequestDispatcher("/WEB-INF/myRequest.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<LeaveRequest> list = lrd.getMyRequest(employee);
        int totalItems = list.size();
        int pageSize = 5;
        int totalPages = (totalItems + pageSize - 1) / pageSize;
        ArrayList<LeaveRequest> listMain = lrd.getMyRequest(employee,1,pageSize);
        req.setAttribute("currentPage", 1);
        req.setAttribute("totalPages", totalPages);
        String department = employee.getDepartment();
        req.setAttribute("department", department);
        req.setAttribute("list", listMain);
        req.getRequestDispatcher("/WEB-INF/myRequest.jsp").forward(req, resp);
    }
}
