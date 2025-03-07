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
public class FeatureDao extends DBcontext {

    public List<String> featureList(int roleid) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT FeatureName\n"
                + "FROM Features f\n"
                + "JOIN RoleFeatures re\n"
                + "ON f.FeatureID = re.FeatureID \n"
                + "WHERE re.RoleID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleid);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                list.add(rs.getString("FeatureName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
