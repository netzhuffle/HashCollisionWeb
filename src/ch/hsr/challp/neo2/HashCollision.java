package ch.hsr.challp.neo2;
import java.util.HashSet;
import java.util.Set;

public class HashCollision {

  private Set<String> users;

  public HashCollision() {
    users = new HashSet<String>();
  }

  public void addUser(String username) {
    users.add(username);
  }

}
