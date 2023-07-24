import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

//funker med iput / output
public class EffektiveMengder{
    public static void main(String[] args) {
        Soketre soketre = new Soketre();
        try {
            File fil = new File(args[0]);
            Scanner myReader = new Scanner(fil);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              soketre.lesData(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public static class Soketre {
    
      public Node rot = null;
      public int size = 0;
  
      public boolean contains(Node v, int x){
         Node k = search(v, x);
         if (k != null){
          return true;
         }
         return false;
          }
      
      public Node search(Node v, int x){
          if (v == null){
              return null;
          }
          if (v.element == x){
              return v;
          }
          if (x < v.element){
              return search(v.venstre, x);
          }
          if(x > v.element){
              return search(v.hoyre, x);
          }
          return null;
      }
  
      public Node insert(Node v, int x){
          if (v == null){
              v = new Node();
              size++;
              v.element = x;
              if(rot == null){
                  rot = v;
              }
          }
          else if(x < v.element){
              v.venstre = insert(v.venstre, x);
          }
          else if (x > v.element){
              v.hoyre = insert(v.hoyre, x);
          }
          return v;
              }
      
      public Node remove(Node v, int x){
          if  (v == null){
              return null;
          }
          if (x < v.element){
              v.venstre = remove(v.venstre, x);
              return v;
          }
          if(x > v.element){
              v.hoyre = remove(v.hoyre, x);
              return v;
          }
          if (v.venstre == null){
              size--;
              return v.hoyre;
          }
          if (v.hoyre == null){
              size--;
              return v.venstre;
          }
          Node u = finnMin(v.hoyre);
          v.element = u.element;
          v.hoyre = remove(v.hoyre, u.element);
          return v;
          }
          
      public Node finnMin(Node v){
              if (v.venstre != null){
                  return finnMin(v.venstre);
              }
              return v;
          }
  
      public int size(Node v){
          return size;
          }
      
      public void lesData(String s){
          String[] splited = s.split(" ");
          if (splited[0].equals("contains")){
              boolean b = contains(rot, Integer.parseInt(splited[1]));
              String string = Boolean.toString(b);
              System.out.println(string);
          }
  
          if (splited[0].equals("insert")){
              insert(rot, Integer.parseInt(splited[1]));
          }
          if (splited[0].equals("remove")){
              rot = remove(rot, Integer.parseInt(splited[1]));
          }
          if (splited[0].equals("size")){
              int stro = size(rot);
              String str = Integer.toString(stro);
              System.out.println(str);
          }
      }
          }
  
          public static class Node {

            public int element;
            public Node venstre = null;
            public Node hoyre = null;
        }
        

}
