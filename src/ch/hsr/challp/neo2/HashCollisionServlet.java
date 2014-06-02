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
    private static final long  serialVersionUID = 3563680876400401266L;
    public static final String PARAM_NAME       = "q~r_s@t!";

    public HashCollisionServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head><title>Hello World Servlet</title></head>");
        writer.println("<body>");
        writer.println("<h1>Registrieren</h1>");
        writer.println("<form method='post'>");
        writer.println("<label>");
        writer.println("Username:");
        writer.println("<input name='" + PARAM_NAME + "'>");
        writer.println("</label>");
        writer.println("<input type='submit'>");
        writer.println("</form>");
        writer.println("<body>");
        writer.println("</html>");

        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        HashCollision hashCollision = new HashCollision();

        hashCollision.addUser(request.getParameter(PARAM_NAME));

        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head><title>Hello World Servlet</title></head>");
        writer.println("<body>");
        writer.println("<h1>Erfolgreich</h1>");
        writer.println("Hallo");
        writer.println(hashCollision.getUser(request.getParameter(PARAM_NAME)));
        writer.println("</form>");
        writer.println("<body>");
        writer.println("</html>");

        writer.close();
        System.out.println("request map size: "
                + request.getParameterMap().size());
        double time = (System.currentTimeMillis() - start) / 1000;
        System.out.println("time: " + time + "s");
    }
}
