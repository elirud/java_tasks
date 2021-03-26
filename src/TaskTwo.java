import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Stream;

public class TaskTwo {

    public static void main(String[] args) {

        try (BufferedReader fileReader =  new BufferedReader(new FileReader(args[0]))) { // Get filename from arguments
            // Apache Commons have fast solutions, but feels overkill to import for this.
            // This solution might be a bit slow if there are a lot of non number lines to handle exceptions for.
            Stream<Long> longStream = fileReader.lines().filter(line -> {
                try {
                    Long.parseLong(line);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }).map(Long::parseLong);

            Comparator<Long> comp = Long::compareTo;

            if (args.length == 2 && args[1].equals("desc")) {
                longStream.sorted(comp.reversed()).forEach(System.out::println);
            } else {
                longStream.sorted().forEach(System.out::println);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
