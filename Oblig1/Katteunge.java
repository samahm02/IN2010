import java.util.HashMap;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Katteunge {
    static String kattpos= null;
    static String kattGren = "tom";
    static HashMap<String, String[]> tre = new HashMap<>();

    public static void main(String[] args) {
        lesinn(args[0]);
        FinnVei();
    }


    public static void lesinn(String filen) {
        File fil = new File(filen);
        try (Scanner myReader = new Scanner(fil)) {
            kattpos = myReader.nextLine();
            String denne=" ";
            while (!denne.equals("-1")) {
                String[] linjen = myReader.nextLine().split(" ");
                denne = linjen[0];
                String[] barn= new String [linjen.length-1]; 
                System.arraycopy(linjen, 1, barn, 0, linjen.length-1);
                tre.put(denne, barn);
                if (Arrays.asList(barn).contains(kattpos)) {
                    kattGren = denne;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

    public static void FinnVei() {
        System.out.println(kattpos);
        while (true) {
            System.out.println(kattGren);
            String sistegren = kattGren;
            for (String nokkel : tre.keySet()) {
                if (Arrays.asList(tre.get(nokkel)).contains(kattGren)) {
                    kattGren = nokkel;
                    break;
                }
            }
            if (sistegren.equals(kattGren)) {
                break;
            }
        }
        
    }

    

    
}
