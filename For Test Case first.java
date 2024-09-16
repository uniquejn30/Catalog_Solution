import java.util.HashMap;
import java.util.Map;

public class ShamirSecretSharing {

    // Function to decode the y-value from its given base
    public static int decodeValue(String base, String value) {
        int baseValue = Integer.parseInt(base);
        return Integer.parseInt(value, baseValue); // Convert value from its given base to base 10 (decimal)
    }

    // Function to perform Lagrange Interpolation and find the constant term (c)
    public static double lagrangeInterpolation(Map<Integer, Integer> points, int k) {
        double result = 0.0;

        // Lagrange interpolation formula
        for (Map.Entry<Integer, Integer> entry1 : points.entrySet()) {
            int xi = entry1.getKey();
            int yi = entry1.getValue();

            double term = yi;
            for (Map.Entry<Integer, Integer> entry2 : points.entrySet()) {
                int xj = entry2.getKey();
                if (xi != xj) {
                    term *= (0 - xj) / (double)(xi - xj); // Using 0 for interpolation since we need the constant term
                }
            }
            result += term;
        }

        return result;
    }

    public static void main(String[] args) {
        // Sample input as a String (manually parsed)
        String jsonString = "{\n" +
                "    \"keys\": {\n" +
                "        \"n\": 4,\n" +
                "        \"k\": 3\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"4\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"base\": \"2\",\n" +
                "        \"value\": \"111\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"12\"\n" +
                "    },\n" +
                "    \"6\": {\n" +
                "        \"base\": \"4\",\n" +
                "        \"value\": \"213\"\n" +
                "    }\n" +
                "}";

        // Manually extract the number of keys and their values
        int n = 4; // Number of roots
        int k = 3; // Minimum number of points to solve the polynomial

        // Manually parsed map of points
        Map<Integer, String[]> pointsInput = new HashMap<>();
        pointsInput.put(1, new String[]{"10", "4"});
        pointsInput.put(2, new String[]{"2", "111"});
        pointsInput.put(3, new String[]{"10", "12"});
        pointsInput.put(6, new String[]{"4", "213"});

        // Map to store the decoded (x, y) points
        Map<Integer, Integer> points = new HashMap<>();

        // Decode the points (x, y) from the map
        for (Map.Entry<Integer, String[]> entry : pointsInput.entrySet()) {
            int x = entry.getKey();
            String[] baseAndValue = entry.getValue();
            String base = baseAndValue[0];
            String value = baseAndValue[1];

            // Decode the y-value from the given base
            int y = decodeValue(base, value);
            points.put(x, y); // Store the decoded (x, y) pair
        }

        // Perform Lagrange interpolation to find the constant term (c)
        double secret = lagrangeInterpolation(points, k);

        // Output the constant term (secret)
        System.out.println("The secret (constant term c) is: " + secret);
    }
}
