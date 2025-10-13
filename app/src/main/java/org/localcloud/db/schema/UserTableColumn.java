package org.localcloud.db.schema;

import org.localcloud.Data.AppProperties;

public enum UserTableColumn implements TableColumn {
    USER_ID("db.schema.user.userid"),
    PASSWORD("db.schema.user.password"),
    ROLE("db.schema.user.role");

    private final String prop;
    UserTableColumn( String prop) {
        this.prop = prop;
    }

    @Override
    public String getColumnName() {
        return AppProperties.get(prop);
    }
}

