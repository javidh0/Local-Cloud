package org.localcloud.IAM;

public record IAMHolder(Long sessionId, UserRole userRole) {
    public Long getSessionId() {
        return sessionId;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
