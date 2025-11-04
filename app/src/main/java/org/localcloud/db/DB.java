package org.localcloud.db;

import org.localcloud.Data.AppProperties;
import org.localcloud.Data.AppPropConstant;
import org.localcloud.DataStructure.Pair;
import org.localcloud.IAM.UserRole;

import java.sql.*;

public class DB {
    private static Connection connection;

    public static void loadDBInstance() {
        if(isConnected()) return;

        String url = AppProperties.get(AppPropConstant.APP_PROPERTIES_DB_URL);
        String user = AppProperties.get(AppPropConstant.APP_PROPERTIES_DB_USERID);
        String password = AppProperties.get(AppPropConstant.APP_PROPERTIES_DB_PASSWORD);

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static boolean isConnected() {
        return connection != null;
    }

    protected static Pair<String, UserRole> getPasswordAndRole(String cmd) throws NullPointerException {
        if(!isConnected()) throw  new NullPointerException("DB Instance Not created!");

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(cmd)) {
            Pair<String, UserRole> pair = new Pair<>();
            rs.next();
            pair.setFirst(rs.getString(org.localcloud.db.schema.UserTableColumn.PASSWORD.getColumnName()));
            pair.setSecond(rs.getString(org.localcloud.db.schema.UserTableColumn.ROLE.getColumnName()).equals("A")? UserRole.ADMIN : UserRole.USER);

            return pair;
        } catch (SQLException e) {
            return null;
        }
    }
}
