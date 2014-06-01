package ch.hsr.challp.neo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
//import sun.misc.Hashing;

public class Hacker {

  public static void main(String[] args) throws Exception {
//    String a = "a";
//    String na = "\0a";
//    String nnna = "\0\0\0a";
//    String an = "a\0";
//    System.out.println(a.hashCode() + "/" + na.hashCode() + "/" + nnna.hashCode() + "/" + an.hashCode());
//    System.out.println(Hashing.stringHash32(a) + "/" + Hashing.stringHash32(na) + "/" + Hashing.stringHash32(nnna) + "/" + Hashing.stringHash32(an));

    URL server = new URL(
        "http://localhost:8080/HashCollisionWeb/HashCollisionServlet");
    HttpURLConnection conn = (HttpURLConnection) server.openConnection();
    conn.setDoOutput(true);

    StringBuilder sb = new StringBuilder(2);
    sb.append('a');
    sb.insert(0, '0');
    String parameter = "name=" + sb.toString();
    sb = null;
    conn.setRequestProperty("Content-Length",
        "" + Integer.toString(parameter.getBytes().length));
    conn.setUseCaches(false);

    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
    writer.write(parameter);
    writer.flush();
    writer.close();

    BufferedReader reader = new BufferedReader(new InputStreamReader(
        conn.getInputStream()));

    reader.close();
    conn.disconnect();
  }
}