
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class UserDao extends DBcontext{
    
    public User get(int userid) {
        String sql = "SELECT * FROM Student where UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserid(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setEmail(rs.getString("Email"));
                u.setRoleid(rs.getInt("RoleID"));
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public User get(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserid(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setEmail(rs.getString("Email"));
                u.setRoleid(rs.getInt("RoleID"));
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
