package ch.hsr.challp.neo2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GoldnuggetServlet
 */
@WebServlet(description = "Shows if the user was successful with his attack and has sent enough colliding parameters", urlPatterns = { "/nugget" })
public class GoldnuggetServlet extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    /** from random.org, guaranteed to be random. */
    private static final String NUGGET           = "3hMzFEH2AJnxqZa9WMwDR9XVY7fsUgWbNdwE7MHPZxFnZbXW";
    /**
     * only 10'000 because if someone can find 10'000 collisions he can easily
     * find more. Also if the number is higher the HL user would have to wait
     * for the server to process the request.
     */
    private static final int    SUCCESS_BARRIER  = 10_000;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head><title>Hello World Servlet</title></head>");
        writer.println("<body>");
        if (HashCollisionServlet.getCollisionCount(request.getRemoteAddr()) > SUCCESS_BARRIER) {
            writer.println("<h1>" + NUGGET + "</h1>");
        } else {
            writer.println("Try harder, better, faster, stronger!");
            // probably turn on to help hackers see if they even had success
            // with some collisions
            // System.out.println("number of collisions: "
            // +
            // HashCollisionServlet.getCollisionCount(request.getRemoteAddr()));
        }

        writer.println("<body>");
        writer.println("</html>");

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
