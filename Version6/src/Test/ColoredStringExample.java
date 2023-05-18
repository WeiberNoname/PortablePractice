package Test;
public class ColoredStringExample {
    public static void main(String[] args) {
        String inputString = "Hello, World! This is a colored string example.";

        // Split the string into separate parts
        String[] parts = inputString.split(" ");

        // Define an array of colors to be used
        String[] colors = { "red", "blue", "green", "yellow" };

        // Loop through the parts and format them with colors
        StringBuilder formattedString = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            // Choose a color from the array based on the index
            String color = colors[i % colors.length];

            // Format the part with the chosen color and add line breaks
            formattedString.append("<font color='" + color + "'>" + parts[i] + "</font><br>");
        }

        // Display the formatted string
        System.out.println("<html>" + formattedString + "</html>");
    }
}
