package ch.hsr.challp.neo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashcollisionPlayground {
    public static void main(String[] args) {
        String[] basestring = new String[4];
        basestring[0] = "q~";
        basestring[1] = "r_";
        basestring[2] = "s@";
        basestring[3] = "t!";

        int stringlength = 10;
        generateCombinations(stringlength, basestring);
        // possibleStrings(stringlength / 2, basestring, "");

        // System.out.println("Ey".hashCode());
        // System.out.println("FZ".hashCode());
        // q~, r_, s@, t!,

        // System.out.println("q~q~r_".hashCode());
        // System.out.println("r_r_s@".hashCode());
        // System.out.println("s@t!s@".hashCode());
        // System.out.println("t!t!q~".hashCode());

        //
        // System.out.println("tttt".hashCode());
        // System.out.println("ttuU".hashCode());
        // System.out.println("ttv6".hashCode());
        // System.out.println("uUv6".hashCode());
    }

    private static final void possibleStrings(int maxLength, String[] alphabet,
            String curr) {

        // If the current string has reached it's maximum length
        if (curr.length() == maxLength) {
            System.out.println(curr);

            // Else add each letter from the alphabet to new strings and process
            // these new strings again
        } else {
            for (String s : alphabet) {
                String oldCurr = curr;
                curr += s;
                possibleStrings(maxLength, alphabet, curr);
                curr = oldCurr;
            }
        }
    }

    private static final void addHash(Map<Integer, List<String>> coll, String s) {
        int h = s.hashCode();
        List<String> bucket = coll.get(h);
        if (bucket == null) {
            bucket = new ArrayList<>();
            coll.put(h, bucket);
        }
        bucket.add(s);
    }

    private static final void findEquivalentTwoCharStrings() {
        final Map<Integer, List<String>> collisions = new HashMap<>(200);
        for (int i = 32; i < 127; i++) {
            for (int j = 32; j < 127; j++) {
                String s = ((char) i) + "" + ((char) j);
                addHash(collisions, s);
            }
        }

        System.out.println(collisions.size());
        for (Map.Entry<Integer, List<String>> e : collisions.entrySet()) {
            if (e.getValue().size() >= 4) {
                System.out.print("size: " + e.getValue().size() + ", h="
                        + e.getKey() + "\n\t");
                for (String s : e.getValue()) {
                    System.out.print(s + ", ");
                }
                System.out.println();
            }
        }

    }

    private static final List<String> generateCombinations(int arraySize,
            String[] possibleValues) {
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
