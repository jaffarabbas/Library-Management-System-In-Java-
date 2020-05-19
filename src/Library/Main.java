package Library;

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
    static Scanner obj = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String filename;
        try {
            File file = new File("E:\\GitHub\\Library-Management-System-In-Java-\\file\\new.docs ");
            if(file.createNewFile()){
                System.out.println("File created");
            }
            else{
                System.out.println("Previous created");
                 filename  ="E:\\GitHub\\Library-Management-System-In-Java-\\file\\new.docs ";
              //  FileWriter writer = new FileWriter(filename,true);
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true));
                PrintWriter pw = new PrintWriter(writer);
              //  FileOutputStream outputStream = new FileOutputStream("E:\\GitHub\\Library-Management-System-In-Java-\\file\\new.docs ", true);
                System.out.println("Enter your name : ");
                String a = obj.nextLine();
                System.out.println("Enter your name : ");
                String b = obj.nextLine();
                System.out.println("Enter your name : ");
                String c = obj.nextLine();
                writer.write("Name : "+a+"\nAge : "+b+"\nAddress : "+c+"\n");
                writer.close();
                System.out.println("FileWriter succesfully");
                BufferedReader dsc = new BufferedReader(new FileReader("E:\\GitHub\\Library-Management-System-In-Java-\\file\\new.docs"));
                String read;
                while ((read = dsc.readLine()) != null){
                    System.out.println(read);

                }
                check.recorddelte(filename,"sdf",1,"\n");
                dsc.close();
            }
        }
        catch (IOException ex){
            System.out.println("Error : "+ex);
        }
    }
}
