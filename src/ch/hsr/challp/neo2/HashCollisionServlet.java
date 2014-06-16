package ch.hsr.challp.neo2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HashCollisionServlet.
 * 
 * </br>
 * 
 * Expects one post parameter {@literal HashCollisionServlet#PARAM_NAME} and
 * prints the given value. Doing some other stuff like reading again from the
 * hashmap to slow it down even more. To slow down the server even a little
 * more, one can search for collisions with the
 * {@link HashCollisionServlet#PARAM_NAME}.
 */
@WebServlet("/HashCollisionServlet")
public class HashCollisionServlet extends HttpServlet {
    private static final long           serialVersionUID = 3563680876400401266L;
    public static final String          PARAM_NAME       = "name";
    private static Map<String, Integer> COLLISION_COUNT  = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head><title>Registration</title></head>");
        writer.println("<!-- the reason why the server might reject your request is possibly a set maxParameterCount config ;-) lower the parameters you send and find the upper limit -->");
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
        COLLISION_COUNT.put(request.getRemoteAddr(), Integer.valueOf(0));
        long start = System.currentTimeMillis();
        HashCollision hashCollision = new HashCollision();

        hashCollision.addUser(request.getParameter(PARAM_NAME));

        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head><title>Registration successful</title></head>");
        writer.println("<body>");
        writer.println("<h1>Registration successful!</h1> ");
        writer.println("Hello");
        writer.println(hashCollision.getUser(request.getParameter(PARAM_NAME)));
        writer.println("</form>");
        writer.println("<body>");
        writer.println("</html>");

        writer.close();
        // log stuff
        System.out.println("request map size: "
                + request.getParameterMap().size());
        double time = (System.currentTimeMillis() - start) / 1000;
        System.out.println("time: " + time + "s");

        // count whether there are enough collisions
        int collisionCountForIp = countCollisions(request.getParameterMap());
        COLLISION_COUNT.put(request.getRemoteAddr(),
                Integer.valueOf(collisionCountForIp));
    }

    /**
     * Could not find another way to count the collisions, unfortunately. But
     * it’s not so time consuming. You could extend
     * {@link HttpServletRequestWrapper} but that still doesn’t help because the
     * parsing of the strings cannot be overriden.
     * 
     * <br/> Time: about 100ms for 65k collisions/entries
     * 
     * @return the highest collision count overall (not accumulated)
     */
    private static final int countCollisions(Map<String, String[]> map) {
        // the highest found number of collisions
        int max = 0;
        /*
         * <hash, number of collisions for this count> using an AtomicInteger
         * since its faster than always override an Integer value (using
         * collisions.put(key, collisions.get(key) + 1);)
         */
        Map<Integer, AtomicInteger> collisions = new HashMap<>();
        for (String key : map.keySet()) {
            // i don’t like autoboxing
            Integer hash = Integer.valueOf(key.hashCode());
            AtomicInteger counter = collisions.get(hash);
            if (counter == null) {
                counter = new AtomicInteger();
                collisions.put(hash, counter);
            }
            counter.incrementAndGet();
            if (counter.intValue() > max) {
                max = counter.intValue();
            }
        }

        return max;
    }

    public static int getCollisionCount(String ipAddr) {
        return COLLISION_COUNT.get(ipAddr).intValue();
    }

}
