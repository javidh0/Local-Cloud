package org.localcloud.db;

import org.localcloud.Data.AppProperties;
import org.localcloud.Data.AppPropConstant;
import org.localcloud.DataStructure.Pair;
import org.localcloud.IAM.UserRole;
import org.localcloud.db.schema.TableColumn;

import java.sql.*;
import java.util.ArrayList;

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

    protected Pair<String, UserRole> getPasswordAndRole(String cmd, TableColumn passCol, TableColumn roleCol) throws NullPointerException {
        if(!isConnected()) throw  new NullPointerException("DB Instance Not created!");

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(cmd)) {
            Pair<String, UserRole> pair = new Pair<>();
            rs.next();
            pair.setFirst(rs.getString(passCol.getColumnName()));
            pair.setSecond(rs.getString(roleCol.getColumnName()).equals("A")? UserRole.ADMIN : UserRole.USER);

            return pair;
        } catch (SQLException e) {
            return null;
        }
    }
}
