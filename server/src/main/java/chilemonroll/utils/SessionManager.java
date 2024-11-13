package chilemonroll.utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
  private static final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();
  private static final long SESSION_DURATION = 24 * 60 * 60 * 1000; // 24 horas en milisegundos

  static class SessionInfo {
    int userId;
    long expirationTime;

    SessionInfo(int userId) {
      this.userId = userId;
      this.expirationTime = System.currentTimeMillis() + SESSION_DURATION;
    }

    boolean isValid() {
      return System.currentTimeMillis() < expirationTime;
    }
  }

  public static String createSession(int userId) {
    String token = UUID.randomUUID().toString();
    sessions.put(token, new SessionInfo(userId));
    return token;
  }

  public static Integer getUserId(String token) {
    SessionInfo info = sessions.get(token);
    if (info != null && info.isValid()) {
      return info.userId;
    }
    sessions.remove(token);
    return null;
  }

  public static void removeSession(String token) {
    sessions.remove(token);
  }
}
