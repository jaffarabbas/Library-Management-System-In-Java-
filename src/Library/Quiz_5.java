//Quiz 2

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class make_class{

   public String gender;
   public String firstname;
   public String lastname;
   public String status;
   public String Toy_Name;
   int coal=0,bicycle=0,Doll=0;
//method 1
 
   public void Method(){
        try{
            //file created List.txt
            String filename = "List.txt";

            File file = new File(filename);

            if(file.createNewFile()){
                System.out.println("File created");
            }
            else{
                shopping_l();
                write_in_list();
                addlist();
            }
        }
        catch(IOException e){
            System.out.println("Error : "+e);
        }
    }
//method 2
    //it will write in list
    public void write_in_list() throws IOException {
	    Scanner obj = new Scanner(System.in);
    	System.out.println("Previous created");
    	        String filename  ="List.txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true));
                PrintWriter printtt = new PrintWriter(writer);
                System.out.println("Enter your Gender : ");
                 gender = obj.nextLine();
                System.out.println("Enter your First name : ");
                 firstname = obj.nextLine();
                System.out.println("Enter your Last name : ");
                 lastname = obj.nextLine();
                System.out.println("Enter your Status : ");
                 status = obj.nextLine();


                writer.write("\n"+gender+"      "+firstname+"\t      "+lastname+"\t  "+status+"\n");
                writer.close();
                System.out.println("FileWriter succesfully");

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String read;

        while ((read = reader.readLine()) != null){
            System.out.println(read);
        }
    }
//method 3
   
    public void shopping_l(){
        try{
            String filename2  ="ShoppingList.txt";
            File file2 = new File(filename2);
            if(file2.createNewFile()){
                System.out.println("Shopping File created");
            }

        }
        catch(IOException e){
            System.out.println("Error : "+e);
        }
    }

    public String get_toy(){
        if(this.gender.equals("F") && this.status.equals("Good")){
            this.Toy_Name = "Doll";
        }
        else if(this.gender.equals("M") && this.status.equals("Good")){
            this.Toy_Name = "Bicycle";
        }
        else{
            this.Toy_Name = "Coal";
        }

        return Toy_Name;
    }
 //method 3
   
    public void addlist() throws IOException {

        String filename = "ShoppingList.txt";
        FileWriter writer = new FileWriter(filename,true);

        PrintWriter print = new PrintWriter(writer);
        increasement();
        writer.write("\n"+firstname+"      "+lastname+"\t"+get_toy()+"\n\n\n\n"+"Coal : "+coal+"\nBicyle : "+bicycle+"\nDoll : "+Doll);
        writer.close();
        System.out.println("FileWriter succesfully");
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String read;
    
        while ((read = reader.readLine()) != null){
            System.out.println(read);
        }
    }

   
    public void increasement(){
            if(get_toy().equals("Doll"))
                Doll++;
            else if(get_toy().equals("Bicycle"))
                bicycle++;
            else if(get_toy().equals("Coal"))
                coal++;
    }

//constructer 
    public make_class()
    {
	    Method();
    }
}

//Main class
public class Quiz_5{
    public static void main(String[] args) {
    	make_class obj = new make_class();
    }
}
