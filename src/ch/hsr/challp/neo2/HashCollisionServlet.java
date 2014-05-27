package ch.hsr.challp.neo2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HashCollisionServlet
 */
@WebServlet("/HashCollisionServlet")
public class HashCollisionServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public HashCollisionServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter writer = response.getWriter();

    writer.println("<html>");
    writer.println("<head><title>Hello World Servlet</title></head>");
    writer.println("<body>");
    writer.println("<h1>Registrieren</h1>");
    writer.println("<form method='post'>");
    writer.println("<label>");
    writer.println("Username:");
    writer.println("<input name='name'>");
    writer.println("</label>");
    writer.println("<input type='submit'>");
    writer.println("</form>");
    writer.println("<body>");
    writer.println("</html>");

    writer.close();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {HashCollision hashCollision = new HashCollision();

      PrintWriter writer = response.getWriter();

      writer.println("<html>");
      writer.println("<head><title>Hello World Servlet</title></head>");
      writer.println("<body>");
      writer.println("<h1>Erfolgreich</h1>");
      writer.println("Hallo");
      writer.println(request.getParameter("name"));
      writer.println("</form>");
      writer.println("<body>");
      writer.println("</html>");

      writer.close();
      
      for (int iterations = 1000; iterations < 100000; iterations += 1000) {
        System.out.print((iterations / 1000.0) + "k iterations: ");
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
          StringBuilder sb = new StringBuilder();
          for (int n = 0; n < i; n++) {
            sb.append("0");
          }
          sb.append("a");
          hashCollision.addUser(sb.toString());
          sb = null;
        }
        System.out.print(((System.currentTimeMillis() - start) / (iterations / 100.0)) + "ms/");
    
        long startHack = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
          StringBuilder sb = new StringBuilder();
          for (int n = 0; n < i; n++) {
            sb.append("\0");
          }
          sb.append("a");
          hashCollision.addUser(sb.toString());
          sb = null;
        }
        System.out.println(((System.currentTimeMillis() - startHack) / (iterations / 100.0)) + "ms");
      }
  }

}
