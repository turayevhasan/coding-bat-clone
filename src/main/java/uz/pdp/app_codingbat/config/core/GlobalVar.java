package uz.pdp.app_codingbat.config.core;

import uz.pdp.app_codingbat.config.UserPrincipal;
import uz.pdp.app_codingbat.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class GlobalVar {

    private final static ThreadLocal<LocalDateTime> START_TIME = ThreadLocal.withInitial(LocalDateTime::now);
     private final static ThreadLocal<String> H_REQUEST_ID = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<UUID> USER_UUID = ThreadLocal.withInitial(UUID::randomUUID);
    private final static ThreadLocal<User> USER = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<UserPrincipal> AUTH_USER = ThreadLocal.withInitial(() -> null);

    public static User getUser() {
        return GlobalVar.USER.get();
    }

    public static void setUser(User user) {
        GlobalVar.USER.set(user);
    }

    public static void setUserPrincipal(UserPrincipal authUser) {
        GlobalVar.AUTH_USER.set(authUser);
    }

    public static void setUserUuid(UUID userUuid) {
        GlobalVar.USER_UUID.set(userUuid);
    }

    public static LocalDateTime getStartTime() {
        return GlobalVar.START_TIME.get();
    }

    public static void initStartTime() {
        GlobalVar.START_TIME.set(LocalDateTime.now());
    }

    public static String getRequestId() {
        return GlobalVar.H_REQUEST_ID.get();
    }

    public static void setRequestId(String requestId) {
        GlobalVar.H_REQUEST_ID.set(requestId);
    }

    public static void clearContext() {
        GlobalVar.USER.remove();
        GlobalVar.USER_UUID.remove();
        GlobalVar.AUTH_USER.remove();
    }

}
