package org.localcloud.db;

import org.localcloud.Data.DBProperties;
import org.localcloud.DataStructure.Pair;
import org.localcloud.DataStructure.Placeholder;
import org.localcloud.IAM.IAMHolder;
import org.localcloud.IAM.UserRole;
import org.localcloud.db.schema.UserTableColumn;

import java.sql.SQLException;

public class UserDB extends DB {
    private static final String USER_ID = "user_id";
    private static final String PROP_AUTH_QUERY = "users.auth.details";

    public IAMHolder authenticateUser(String userid, String password) throws SQLException, NullPointerException {
        Placeholder queryTemplate = DBProperties.getParametrizedProp(PROP_AUTH_QUERY);

        assert queryTemplate != null;
        queryTemplate.setValue(USER_ID, "'" + userid + "'");

        String psql = queryTemplate.replacePlaceholders();
        Pair<String, UserRole> userAuth = getPasswordAndRole(psql, UserTableColumn.PASSWORD, UserTableColumn.ROLE);

        return new IAMHolder(0L, userAuth.getSecond());
    }
}
