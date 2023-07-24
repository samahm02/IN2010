import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

//funker nå med input 
public class KoeMain {
    
    public static void main(String[] args) {
        Koe koe = new Koe();
        try {
            //File fil = new File("tests/oppgave2-src/inputs/stdin.txt");
            File fil = new File("inputs/stdin.txt");
            Scanner myReader = new Scanner(fil);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              koe.lesData(data);
              //System.out.println(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public static class Koe {
    
        Noden forste;
        Noden siste;
        int lengde = 0;
    
        public void push_back(Noden x){
            if (forste == null){
                //System.out.println(x.verdi+ "test");
                forste = x;
                siste = x;
                lengde++;
            }
            else {
                /* siste.neste = x;
                siste = x;
                lengde++; */
                int i = 0;
                Noden temp = forste;
                //System.out.println(lengde);
                while(i < lengde-1){
                    temp = temp.neste;
                    //System.out.println("i: " + temp.verdi);
                    i++;
                }
                temp.neste = x;
                lengde++;
                //System.out.println(x.verdi+"teat");
            }
            //System.out.println(lengde +" legnde bak");

        }
    
        public void push_front(Noden x){
            if (forste == null){
                //System.out.println(x.verdi+ "test");
                forste = x;
                siste = x;
                lengde++;
            }
            else {
                //System.out.println(x.verdi+ "test");
                x.neste = forste;
                forste = x;
                lengde++;
            }
            //System.out.println(lengde+" legnde");

        }
  /*        
        public void push_middle(Noden x){
            if (forste == null){
                forste = x;
                siste = x;
                lengde++;
            }
            int pos = ((lengde + 1)/2);
            int i = 0;
            Noden temp = null;
            Noden tempNeste = forste;
            Boolean test = false;
            while (i < pos){
                temp = tempNeste;
                tempNeste = tempNeste.neste;
                i++;
                test = true;
            }
            if(test){
                x.neste = tempNeste;
                temp.neste = x;
            }
            else{
                forste.neste = x;
                siste = x;
            }
        }
     */

     public void push_middle(Noden x){
        if (forste == null){
            forste = x;
            siste = x;
            lengde++;
        }
        else{
        int pos = ((lengde + 1)/2);
        //System.out.println(pos);
        //System.out.println(lengde +" legnde");
        //System.out.println(pos+ " pos");
        int i = 1;
        Noden temp = forste;
        //System.out.println("Start-----------");
        while(i < pos){
        temp = temp.neste;
        i++;
        //System.out.println(temp.verdi);

        }
        //System.out.println("Slutt-----------");
        x.neste = temp.neste;
        temp.neste = x;
        lengde++;
        //System.out.println(x.verdi);
        }
        //System.out.println(lengde+" legnde");


     }
        public Noden get(int pos){
            //System.out.println("lengden: " + lengde);
            if(pos <= lengde-1){
                int i = 0;
                Noden temp = forste;
                while ((i < pos) /*&& (temp != null)*/){
                    //System.out.println("ListeElement" + i + " verdi:"+ temp.verdi);
                    temp = temp.neste;
                    i++;
                }
                return temp;
            }
           return null;
        }
    //zero     i = 1 and index 1 
        public void lesData(String s){
            String[] splited = s.split(" ");
    
            if (splited[0].equals("push_back")){
                Noden x = new Noden(Integer.parseInt(splited[1]));
                push_back(x);
            }
    
            if (splited[0].equals("push_front")){
                Noden x = new Noden(Integer.parseInt(splited[1]));
                push_front(x);
            }
            if (splited[0].equals("push_middle")){
                Noden x = new Noden(Integer.parseInt(splited[1]));
                push_middle(x);
            }
            if (splited[0].equals("get")){
                Noden n = get(Integer.parseInt(splited[1]));
                if (n != null){
                    System.out.println(n.verdi);
                } 
                else{
                    System.out.println("Wooptio");
                }
            }
        }
    
    }
    
    public static class Noden {
        Noden neste;
        int verdi;
    
        public Noden(int v){
            verdi = v;
        }
        //Noden forrige;
    }
    
}
