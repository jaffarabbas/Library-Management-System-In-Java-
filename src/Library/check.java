package Library;

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;



public class check {
    public static void recorddelte(String filepath,String removeterm,int positionofterm,String delemat){

//        try{
//            File file = new File("E:\\GitHub\\Library-Management-System-In-Java-\\file\\temp.docs ");
//        }
//        catch (Exception e){
//            System.out.println("error : "+e);
//        }


        int position = positionofterm -1;
        String tempfile = "E:\\GitHub\\Library-Management-System-In-Java-\\file\\temp.docs";
        File oldfile = new File(filepath);
        File newFile = new File(tempfile);


        String currentline;
        String data[];

        try {
            FileWriter writer = new FileWriter(tempfile,true);
            BufferedWriter bufwriter = new BufferedWriter(writer);
            PrintWriter print = new PrintWriter(bufwriter);

            FileReader fileread = new FileReader(filepath);
            BufferedReader bufreader = new BufferedReader(fileread);

            while ((currentline = bufreader.readLine()) != null){
                data = currentline.split("\n");
                if(!(data[position].equalsIgnoreCase(removeterm))){
                    print.println(currentline);
                }
            }

            print.flush();
            print.close();
            fileread.close();
            bufwriter.close();
            bufreader.close();
            writer.close();

            final boolean delete = oldfile.delete();
            File dump = new File(filepath);
            final boolean b = newFile.renameTo(dump);
        }
        catch (Exception e){
            System.out.println("Error :> "+e);
        }
    }

//    public static void main(String[] args) {
//        check.recorddelte();
//    }
}
