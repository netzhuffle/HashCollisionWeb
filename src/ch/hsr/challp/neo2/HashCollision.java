package ch.hsr.challp.neo2;
import java.util.HashMap;
import java.util.Map;

public class HashCollision {

  private Map<String, String> users;

  public HashCollision() {
    users = new HashMap<String, String>();
  }

  public void addUser(String username) {
    users.put(username, username);
  }

  public String getUser(String username) {
    return users.get(username);
  }

}
