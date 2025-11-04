package org.localcloud.IAM;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserSessionsHandler {
    private static final Map<String, IAMHolder> activeSessions = new HashMap<>();
    private static final Long maxInactivityInMins = 1L;

    public static String getNextId() {
        return UUID.randomUUID().toString();
    }

    public static IAMHolder getIAMHolder(String sessionId) {
        IAMHolder iamHolder = activeSessions.getOrDefault(sessionId, null);

        long inactiveTime = getInactiveMillis(iamHolder);
        if(inactiveTime <= maxInactivityInMins*60*1000) return iamHolder;

        activeSessions.remove(sessionId);
        return null;
    }

    public static boolean isSessionActive(Long sessionId) {
        IAMHolder iamHolder = activeSessions.getOrDefault(sessionId, null);
        return getInactiveMillis(iamHolder) <= maxInactivityInMins*60;
    }

    private static Long getInactiveMillis(IAMHolder iamHolder) {
        if(iamHolder == null || iamHolder.getCreatedTime() == null) return Long.MAX_VALUE;
        return Calendar.getInstance().getTimeInMillis() - iamHolder.getCreatedTime();
    }

    public static void setActiveSessions(String sessionId, IAMHolder iamHolder) {
        activeSessions.put(sessionId, iamHolder);
    }
}
