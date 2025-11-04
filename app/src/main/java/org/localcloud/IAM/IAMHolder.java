package org.localcloud.IAM;

import java.util.Calendar;
import java.util.HashMap;

public class IAMHolder {
    private final String sessionId;
    private final HashMap<String, Object> userData;
    private final Long createdTime;

    public IAMHolder(HashMap<String, Object> userData) {
        this.userData = userData;
        sessionId = UserSessionsHandler.getNextId();
        this.createdTime = Calendar.getInstance().getTimeInMillis();

        UserSessionsHandler.setActiveSessions(sessionId, this);
    }

    public Object get(String key) {
        return userData.getOrDefault(key, null);
    }

    public String getSessionId() {
        return sessionId;
    }

    public HashMap<String, Object> getUserData() {
        return userData;
    }

    Long getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return "IAMHolder\n" + userData.toString() + "\n" + sessionId + "\n" + createdTime;
    }
}
