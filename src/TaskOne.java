import java.io.FileWriter;
import java.util.Random;
import java.io.IOException;

public class TaskOne {

    public static void main(String[] args) {

        Random rand = new Random();

        try (FileWriter fileWriter =  new FileWriter(args[1])){ // Get filename from arguments

            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                    fileWriter.write(rand.nextInt(100) + "\n"); // Write random int between 0 and 100 to file
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
