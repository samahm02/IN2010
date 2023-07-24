import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Innlevering2 {
    public static void main(String[] args) throws Exception {
        String filename = args[0];
        File file = new File(filename);
        BufferedReader in = new BufferedReader(new FileReader(file));
        int[] A = in.lines().mapToInt(i -> Integer.parseInt(i)).toArray();
        in.close();

        Innlevering2Runner.runAlgsPart1(A, filename);
        Innlevering2Runner.runAlgsPart2(A, filename);
    }
}
