import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.util.PriorityQueue;

public class BalansertSoketreHeap {
    static PriorityQueue<Integer> liste = new PriorityQueue<>();

    public static void main(String[] args) {
        try {
            File fil = new File(args[0]);
            Scanner myReader = new Scanner(fil);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                liste.offer(Integer.parseInt(data));
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        FinnBarn(liste);

    }
    public static void FinnBarn(PriorityQueue<Integer> List){
        if(List.size()==0){ // ikke gjør noe dersom tom liste
            
        }
        else if(List.size() == 1){
            System.out.println(List.poll());
        }
        else{
            int i=(List.size())/2; 
            PriorityQueue<Integer> listeIx = new PriorityQueue<>(); //lagger en liste med elementene mindre en i

            int ix = 0;
            int S = 0;
            while(ix < i){
                S = List.poll();
                listeIx.offer(S);
                ix++;
            }
            S = List.poll();
            System.out.println(S);
            FinnBarn(listeIx);
            FinnBarn(List);
        }

    }
}