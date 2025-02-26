
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.User;

public class EmployeeDao extends DBcontext {

    public Employee get(User user) {
        String sql = "SELECT e.*\n"
                + "FROM Employees e\n"
                + "JOIN Users u ON e.UserID = u.UserID\n"
                + "where u.UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user.getUserid());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeid(rs.getInt("EmployeeID"));
                e.setFullname(rs.getString("FullName"));
                e.setUserid(user.getUserid());
                e.setManagerid(rs.getInt("ManagerID"));
                e.setDepartment(rs.getString("Department"));
                e.setHireDate(rs.getDate("HireDate"));
                return e;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
