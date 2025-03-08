/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author minho
 */
public class DepartmentDao extends DBcontext {

    public List<String> getDepartment(int employeeid) {
        List<String> list = new ArrayList<>();
        String sql = "select Department\n"
                + "from Employees\n"
                + "where EmployeeID != ?\n"
                + "group by Department";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, employeeid);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                list.add(rs.getString("Department"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
