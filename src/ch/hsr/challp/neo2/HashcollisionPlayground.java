package ch.hsr.challp.neo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class containing methods to find new collisions and other misc. stuff.
 */
public class HashcollisionPlayground {
    public static void main(String[] args) {
        String[] basestring = new String[4];
        basestring[0] = "q~";
        basestring[1] = "r_";
        basestring[2] = "s@";
        basestring[3] = "t!";

        long start = System.currentTimeMillis();
        System.out.println("start: " + start);
        int stringlength = 8;
        List<String> foo = generateCombinations(stringlength, basestring);
        System.out.println("generating combinations (since start): "
                + (System.currentTimeMillis() - start) + "ms");
        Map<String, String[]> collisions = new HashMap<>();
        for (String f : foo) {
            collisions.put(f, new String[] { "" });
        }
        System.out.println("unnecessary unimportant stuff (since start): "
                + (System.currentTimeMillis() - start) + "ms");
        System.out.println(countCollisions(collisions) + " collisions");
        System.out.println("counting collisions (since start): "
                + (System.currentTimeMillis() - start) + "ms");
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

    private static final int countCollisions(Map<String, String[]> map) {
        long startCounter = System.currentTimeMillis();

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
        System.out.println(System.currentTimeMillis() - startCounter
                + "ms for counting collisions");
        return max;
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
