
import java.util.Arrays;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class BalansertSoketre {
    static ArrayList<String> liste = new ArrayList<String>();

    public static void main(String[] args) {
        try {
            File fil = new File(args[0]);
            Scanner myReader = new Scanner(fil);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                liste.add(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        Object[] nylist = liste.toArray();
        FinnBarn(nylist);





    }
    public static void FinnBarn(Object[] List){
        if(List.length==0){
            
        }
        else{
            int i=List.length/2;
            System.out.println(List.length);

            System.out.println(List[i]);
            Object[] Test1 =Arrays.copyOfRange(List,0,i);
            Object[] Test2 =Arrays.copyOfRange(List,i+1,List.length);
            FinnBarn(Test1);
            FinnBarn(Test2);


        }

    }
}