
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
        if(user != null){
            Employee employee = ed.get(user);
            return employee;
        }
        return null;
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
