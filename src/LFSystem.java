import ItemPack.Item;

import java.util.ArrayList;
import java.util.Scanner;

public class LFSystem {
    public Item[] lostList;
    public Item[] foundList;
    public Item[] historyList;

    public ArrayList<User> userList;
    int size = 0;

    public LFSystem(){
        lostList = null;
        foundList = null;
        historyList = null;
        userList = new ArrayList<>();
    }

    //Methods
    public void addLostItem(Item a){}
    public void addFoundItem(Item a){}
    public void searchItem(Item a){}

    public void createUser(String name, String id, String contactNo, String course, int year){
        User user = new User(name, id, contactNo, course, year);
        userList.add(user);

    }

    public void printUserList(){
        System.out.println("There are " + userList.size() + " user/s");
        System.out.println("Printing the user list...");
        for(User a : userList){
            System.out.println(a.getId() + ", " + a.getName() + ", " + a.getContactNo() + ", " + a.getCourse() + "-" + a.getYear());
        }
    }

    //TEST FOR THE SYSTEM'S PROCESS, u might be able to use code here adn integrate it into the form!
    public void runSystem(){


        Scanner sc = new Scanner(System.in);
        String input = "", user_id, user_name, user_contact, user_course;
        int user_year;

        while(true){
            System.out.println("Creating user...");
            System.out.print("ID: ");
            user_id = sc.nextLine();
            System.out.print("Name: ");
            user_name = sc.nextLine();
            System.out.print("Contact No: ");
            user_contact = sc.nextLine();
            System.out.print("Course: ");
            user_course = sc.nextLine();
            System.out.print("Year: ");
            user_year = sc.nextInt();

            createUser(user_name, user_id, user_contact, user_course, user_year);

            System.out.println("Create another user? Y/N");
            sc.nextLine();
            input = sc.nextLine();
            if(input.equals("N") || input.equals("n")){
                break;
            }
        }

        printUserList();
    }
}
