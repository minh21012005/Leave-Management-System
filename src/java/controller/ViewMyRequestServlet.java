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

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<LeaveRequest> list = lrd.getMyRequest(employee);
        String department = employee.getDepartment();
        req.setAttribute("department", department);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/myRequest.jsp").forward(req, resp);
    }
}
