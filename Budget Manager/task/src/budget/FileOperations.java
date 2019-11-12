package budget;

import java.io.*;
import java.util.ArrayList;

public class FileOperations {

     static void saveToFile(){

        try (FileOutputStream file = new FileOutputStream("purchases.txt")){
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(Category.all);
            out.writeObject(Main.accountBalance);
            out.close();

            System.out.println("Purchases were saved!");

        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        } catch (IOException e){
            System.out.println("Problem with saving to file.");
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
     static void readFromFile(){
        try (FileInputStream file = new FileInputStream("purchases.txt")){

            ObjectInputStream in = new ObjectInputStream(file);
            Category.all = (ArrayList) in.readObject();;
            Main.accountBalance = (double) in.readObject();
            in.close();

            System.out.println("Purchases were loaded!");

        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        } catch (IOException e){
            System.out.println("Problem with saving to file.");
        } catch (ClassNotFoundException e){
            System.out.println("Cant found the desired class.");
        }
    }

}
