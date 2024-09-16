import java.util.HashMap;
import java.util.Map;

public class ShamirSecretSharing {

    // Function to decode the y-value from its given base
    public static long decodeValue(String base, String value) {
        int baseValue = Integer.parseInt(base);
        return Long.parseLong(value, baseValue); // Convert value from its given base to base 10 (decimal)
    }

    // Function to perform Lagrange Interpolation and find the constant term (c)
    public static double lagrangeInterpolation(Map<Integer, Long> points, int k) {
        double result = 0.0;

        // Lagrange interpolation formula
        for (Map.Entry<Integer, Long> entry1 : points.entrySet()) {
            int xi = entry1.getKey();
            long yi = entry1.getValue();

            double term = yi;
            for (Map.Entry<Integer, Long> entry2 : points.entrySet()) {
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
        // Updated input as a String
        String jsonString = "{\n" +
                "    \"keys\": {\n" +
                "        \"n\": 9,\n" +
                "        \"k\": 6\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"28735619723837\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"base\": \"16\",\n" +
                "        \"value\": \"1A228867F0CA\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"base\": \"12\",\n" +
                "        \"value\": \"32811A4AA0B7B\"\n" +
                "    },\n" +
                "    \"4\": {\n" +
                "        \"base\": \"11\",\n" +
                "        \"value\": \"917978721331A\"\n" +
                "    },\n" +
                "    \"5\": {\n" +
                "        \"base\": \"16\",\n" +
                "        \"value\": \"1A22886782E1\"\n" +
                "    },\n" +
                "    \"6\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"28735619654702\"\n" +
                "    },\n" +
                "    \"7\": {\n" +
                "        \"base\": \"14\",\n" +
                "        \"value\": \"71AB5070CC4B\"\n" +
                "    },\n" +
                "    \"8\": {\n" +
                "        \"base\": \"9\",\n" +
                "        \"value\": \"122662581541670\"\n" +
                "    },\n" +
                "    \"9\": {\n" +
                "        \"base\": \"8\",\n" +
                "        \"value\": \"642121030037605\"\n" +
                "    }\n" +
                "}";

        // Manually extract the number of keys and their values
        int n = 9; // Number of roots
        int k = 6; // Minimum number of points to solve the polynomial

        // Manually parsed map of points
        Map<Integer, String[]> pointsInput = new HashMap<>();
        pointsInput.put(1, new String[]{"10", "28735619723837"});
        pointsInput.put(2, new String[]{"16", "1A228867F0CA"});
        pointsInput.put(3, new String[]{"12", "32811A4AA0B7B"});
        pointsInput.put(4, new String[]{"11", "917978721331A"});
        pointsInput.put(5, new String[]{"16", "1A22886782E1"});
        pointsInput.put(6, new String[]{"10", "28735619654702"});
        pointsInput.put(7, new String[]{"14", "71AB5070CC4B"});
        pointsInput.put(8, new String[]{"9", "122662581541670"});
        pointsInput.put(9, new String[]{"8", "642121030037605"});

        // Map to store the decoded (x, y) points
        Map<Integer, Long> points = new HashMap<>();

        // Decode the points (x, y) from the map
        for (Map.Entry<Integer, String[]> entry : pointsInput.entrySet()) {
            int x = entry.getKey();
            String[] baseAndValue = entry.getValue();
            String base = baseAndValue[0];
            String value = baseAndValue[1];

            // Decode the y-value from the given base
            long y = decodeValue(base, value);
            points.put(x, y); // Store the decoded (x, y) pair
        }

        // Perform Lagrange interpolation to find the constant term (c)
        double secret = lagrangeInterpolation(points, k);

        // Output the constant term (secret)
        System.out.println("The secret (constant term c) is: " + secret);
    }
}
