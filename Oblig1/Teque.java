import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class Teque {
    
    public static void main(String[] args) {
        Koe koe = new Koe();
        try {
            File fil = new File(args[0]);
            Scanner myReader = new Scanner(fil);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              koe.lesData(data);
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
                forste = x;
                siste = x;
                lengde++;
            }
            else {
                int i = 0;
                Noden temp = forste;
                while(i < lengde-1){
                    temp = temp.neste;
                    i++;
                }
                temp.neste = x;
                lengde++;
            }

        }
    
        public void push_front(Noden x){
            if (forste == null){
                forste = x;
                siste = x;
                lengde++;
            }
            else {
                x.neste = forste;
                forste = x;
                lengde++;
            }

        }
  
     public void push_middle(Noden x){
        if (forste == null){
            forste = x;
            siste = x;
            lengde++;
        }
        else{
        int pos = ((lengde + 1)/2);
        int i = 1;
        Noden temp = forste;
        while(i < pos){
        temp = temp.neste;
        i++;

        }
        x.neste = temp.neste;
        temp.neste = x;
        lengde++;
        }


     }
        public Noden get(int pos){
            if(pos <= lengde-1){
                int i = 0;
                Noden temp = forste;
                while ((i < pos)){
                    temp = temp.neste;
                    i++;
                }
                return temp;
            }
           return null;
        }

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
    }
    
}
