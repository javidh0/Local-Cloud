package org.localcloud.server;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.localcloud.Data.AppPropConstant;
import org.localcloud.Data.AppProperties;
import org.localcloud.IAM.IAMHolder;
import org.localcloud.IAM.UserSessionsHandler;
import org.localcloud.server.handlers.LoginHandler;

import java.util.HashMap;

public class MainServer {
    private static Integer cookieExpireTime;
    public static void startServer() {
        cookieExpireTime = Integer.valueOf(AppProperties.get(AppPropConstant.APP_PROPERTIES_COOKIE_SESSION_EXPIRE));
        Javalin app = Javalin.create().start(7070);
        app.get("/", MainServer::loginHandler);
    }

    private static void loginHandler(Context context) {
        try {
            IAMHolder iamHolder = getCookieIAMHolder(context);
            context.cookie("new_user", "false");

            if(iamHolder == null) {
                LoginHandler handler = context.bodyAsClass(LoginHandler.class);
                iamHolder = handler.authenticateUser();
                context.cookie("username", handler.getUsername());
                context.cookie("new_user", "true");
            }

            context.removeCookie("sessionid");
            context.cookie("sessionid", iamHolder.getSessionId(), cookieExpireTime);

            HashMap<String, Object> map = iamHolder.getUserData();
            for(String key: map.keySet()) {
                context.cookie(key, map.get(key).toString());
            }

            context.result("Hello welcome to local cloud\n" );
        }
        catch (Exception e) {
            context.result(e.toString());
        }
    }

    private static IAMHolder getCookieIAMHolder(Context context) {
        String cookieSessionID = context.cookie("sessionid");
        return UserSessionsHandler.getIAMHolder(cookieSessionID);
    }

    private static void updateCookie(Context context, String key, String value) {
        context.cookie(key, value);
    }
}
