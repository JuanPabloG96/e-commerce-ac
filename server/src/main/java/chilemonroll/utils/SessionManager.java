package chilemonroll.utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
  private static final Map<String, Integer> sessions = new ConcurrentHashMap<>();

  public static String createSession(int userId) {
    String token = UUID.randomUUID().toString();
    sessions.put(token, userId);
    return token;
  }

  public static Integer getUserId(String token) {
    return sessions.get(token);
  }

  public static void removeSession(String token) {
    sessions.remove(token);
  }

}
