package controller;

import dal.EmployeeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Employee;
import model.User;

public abstract class BaseRequiredAuthenticationController extends HttpServlet {

    private User getAuthenticatedUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    private Employee getAuthenticatedEmployee(HttpServletRequest req) {
        User user = getAuthenticatedUser(req);
        EmployeeDao ed = new EmployeeDao();
        if (user != null && isAuthorized(req, user)) {
            Employee employee = ed.get(user);
            return employee;
        }
        return null;
    }

    public boolean isAuthorized(HttpServletRequest request, User user) {
    String contextPath = request.getContextPath(); // "/company"
    String requestURI = request.getRequestURI();
    String path = requestURI.substring(contextPath.length());

    // Nếu request tới file .jsp (trừ login.jsp) thì chặn luôn
    if (path.endsWith(".jsp") && !path.equals("/login.jsp")) {
        return false;
    }

    // Nếu chưa login (user == null) thì chỉ cho phép login.jsp
    if (user == null) {
        return path.equals("/login.jsp");
    }

    // Đã login thì kiểm tra quyền theo role
    int roleId = user.getRoleid();

    switch (roleId) {
        case 1:
            return path.equals("/reviewrequest");
            
        case 2:
            return path.equals("/myrequest") || 
                   path.equals("/reviewrequest") || 
                   path.equals("/create");
                   
        case 3:
            return path.equals("/myrequest") || 
                   path.equals("/create");
                   
        default:
            return false;
    }
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = getAuthenticatedEmployee(req);
        if (employee != null) {
            doPost(req, resp, employee);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = getAuthenticatedEmployee(req);
        if (employee != null) {
            doGet(req, resp, employee);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException;

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException;
}
