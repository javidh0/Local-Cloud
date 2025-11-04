package org.localcloud.db;

import org.localcloud.Data.DBProperties;
import org.localcloud.DataStructure.Pair;
import org.localcloud.DataStructure.Placeholder;
import org.localcloud.IAM.IAMHolder;
import org.localcloud.IAM.UserRole;
import org.localcloud.exceptions.AuthenticationException;
import org.localcloud.exceptions.PropertiesException;

import java.sql.SQLException;
import java.util.HashMap;

public class UserDB extends DB {
    private static final String USER_ID = "user_id";
    private static final String PROP_AUTH_QUERY = "users.auth.details";

    public static IAMHolder authenticateUser(String userid, String password) throws SQLException, NullPointerException {
        Placeholder queryTemplate = DBProperties.getParametrizedProp(PROP_AUTH_QUERY);

        if(queryTemplate == null) throw new PropertiesException("unable to get " + PROP_AUTH_QUERY);

        queryTemplate.setValue(USER_ID, "'" + userid + "'");

        String psql = queryTemplate.replacePlaceholders();
        Pair<String, UserRole> userAuth = getPasswordAndRole(psql);

        if(userAuth == null || !userAuth.getFirst().equals(password)) throw new AuthenticationException("user authentication failed");

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("user_role", userAuth.getSecond());

        return new IAMHolder(userData);
    }
}
