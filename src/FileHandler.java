import java.util.Scanner;
import java.io.*;

public class FileHandler{
    public Scanner input = new Scanner(System.in);
    private String path = "";

    public void write(String p){
        this.path = p;
        System.out.println("You are writing in file: " + path + " Enter [Space] to cancel.");
        String str = "";
        while(true){
            str = input.nextLine();
            if(str.equals("[Space]")){
                System.out.println("Press the actual space key");
            }

            if(str.equals(" ")){
                break;
            }

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))){

                bw.write(str);
                bw.newLine();
            }
            catch(FileNotFoundException e){
                System.out.println("FILE NOT FOUND WHEN WRITING");
            }
            catch(IOException e){
                System.out.println("Error writing");
            }
        }
        System.out.println("Done Writing");

    }

    public void read(String p){
        this.path = p;
        System.out.println("File Contents of " + path + ": ");
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String s;
            while((s = br.readLine()) != null){
                System.out.println(s);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("FILE NOT FOUND WHEN READING");
        }
        catch(IOException e){
            System.out.println("Error reading");
        }
        System.out.println("Done Reading");
    }


}