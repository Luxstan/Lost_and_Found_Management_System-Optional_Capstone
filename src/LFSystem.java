import ItemPack.*;

import java.util.ArrayList;
import java.util.Scanner;

public class LFSystem {
    Scanner sc = new Scanner(System.in);
    public ArrayList<User> userList;
    public ArrayList<Item> lostList;
    public ArrayList<Item> foundList;
    public ArrayList<Item> historyList; //MIGHT NOT NEED THIS

    public int item_id = 1;

    public LFSystem(){
        lostList = new ArrayList<>();
        foundList = new ArrayList<>();
        historyList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    //Methods
    public void addLostItem(Item a){}
    public void addFoundItem(Item a){}
    public void searchItem(Item a){}

    public void createUser(String name, String id, String pass, String contactNo, String course, int year){
        User user = new User(name, id, pass, contactNo, course, year);
        userList.add(user);
    }

    public void printUserList(){
        System.out.println("There are " + userList.size() + " user/s");
        System.out.println("Printing the user list...");
        for(User a : userList){
            System.out.println(a.getId() + ", " + a.getName() + ", " + a.getContactNo() + ", " + a.getCourse() + "-" + a.getYear());
        }
        return;
    }

    public Item reportItemLost(User a){
        Item item = null;
        //inputs
        String input;
        double d_input;

        do {
            System.out.println("WHICH ITEM TYPE FITS THE MOST:");
            System.out.println("1 - MONEY");
            System.out.println("2 - DOCUMENT");
            System.out.println("3 - ACCESSORY");
            System.out.println("4 - BAG");
            System.out.println("5 - CLOTHING");
            System.out.println("6 - ELECTRONIC");
            System.out.println("7 - WEARABLE ELECTRONIC");
            System.out.println("8 - FOOD CONTAINER");
            System.out.println("9 - OTHER");
            System.out.println("X - EXIT");
            input = sc.nextLine();
            switch (input) {
                case "1":
                    System.out.println("CHOSEN ITEM TYPE: MONEY");
                    System.out.print("AMOUNT: ");
                    d_input = sc.nextDouble();
                    System.out.print("FOUND IN WALLET? Y/N: ");
                    sc.nextLine();
                    input = sc.nextLine();
                    if(input.equals("Y") || input.equals("y")){
                        item = new Money(d_input, true); //AMOUNT, inWALLET?
                    } else if (input.equals("N") || input.equals("n")) {
                        item = new Money(d_input, false); //AMOUNT, inWALLET?
                    }
                    else{
                        System.out.println("INVALID INPUT!");
                    }

                    break;
                case "2":
                    System.out.println("CHOSEN ITEM TYPE: DOCUMENT");
                    item = new Document();
                    break;
                case "3":
                    System.out.println("CHOSEN ITEM TYPE: ACCESSORY");
                    item = new Accessory();
                    break;
                case "4":
                    System.out.println("CHOSEN ITEM TYPE: BAG");
                    item = new Bag();
                    break;
                case "5":
                    System.out.println("CHOSEN ITEM TYPE: CLOTHING");
                    item = new Clothing();
                    break;
                case "6":
                    System.out.println("CHOSEN ITEM TYPE: ELECTRONIC");
                    item = new Electronic();
                    break;
                case "7":
                    System.out.println("CHOSEN ITEM TYPE: WEARABLE ELECTRONIC");
                    item = new WearableElectronic();
                    break;
                case "8":
                    System.out.println("CHOSEN ITEM TYPE: FOOD CONTAINER");
                    item = new FoodContainer();
                    break;
                case "9":
                    System.out.println("CHOSEN ITEM TYPE: OTHER - MISCELLANEOUS");
                    item = new Miscellaneous();
                    break;
                case "X":
                    System.out.println("EXITING...");
                    break;
                default:
                    System.out.println("INVALID INPUT!");
                    break;
            }

            if(item != null){
                a.addLostItem(item);
                item.setItemID(item_id++);
                System.out.println("ADDED AN ITEM");
            }
            item = null;
        } while (!input.equals("X"));
        return item;
    }

    public Item reportItemFound(User a){
        Item item = new Item();
        a.addFoundItem(item);
        return item;
    }

    //TEST FOR THE SYSTEM'S PROCESS, u might be able to use code here and integrate it into the form!
    /* Bare with me, but I love documenting, so I'll explain the process, may be useful when integrating in your system.
        THE MAIN INTERFACE:
        runs with the runSystem() function, when you log in, a separate runSystem function is called
        this function uses polymorphism to have the same name, it needs 2 parameters: user_id and user_password.

        USER INDEPENDENT SYSTEM:
        When the runSystem(-,-) is called, it first validates the entered id and password, if there is a typo, or it does not exist.
        It will return to the previous runSystem() function.
        If it IS valid, The user is prompted [currently] with 4 options [excluding the exit].

        REPORTING AN ITEM:
        When the user presses 1 or 2, another function is called. reportItemLost(User) or reportItemFound(User).
        The functions CREATE a new ITEM object, then several prompts will show to fill the ITEM'S IDENTITY.
        This uses PROTOTYPING as users may need to leave other prompts blank. NOTE THAT itemName IS A REQUIRED FIELD.
        A special item_id will be generated based on the total items in the system.
        The function will also call either the addLostItem(Item) or the addFoundItem(Item) lists in the specific User.
        It just adds the item to the user's lost / found item lists.

        1/2 -> call reportItemLost() or reportItemFound -> prompt for item creation -> call user.addLostItem or user.addFoundItem -> return

        VIEWING USER'S LIST
        When the user presses 3 or 4, their specific found and lost list wil be displayed.

    * */
    public void runSystem(){
        String input = "", user_id, user_pass, user_name, user_contact, user_course;
        String inputted_id, inputted_pass;
        int user_year;


        do {
            System.out.println("==LOST AND FOUND SYSTEM==");
            System.out.println("1 - Log In");
            System.out.println("2 - Register");
            System.out.println("3 - Print User List");
            System.out.println("4 - Exit");

            input = sc.nextLine();
            switch (input) {
                case "1":
                    System.out.println("LOGGING IN...");
                    System.out.print("Enter ID: ");
                    inputted_id = sc.nextLine();
                    System.out.print("Enter password: ");
                    inputted_pass = sc.nextLine();
                    runSystem(inputted_id, inputted_pass);
                    break;
                case "2":
                    System.out.println("CREATING USER...");
                    System.out.print("ID: ");
                    user_id = sc.nextLine();
                    System.out.print("Password: ");
                    user_pass = sc.nextLine();
                    System.out.print("Name: ");
                    user_name = sc.nextLine();
                    System.out.print("Contact No: ");
                    user_contact = sc.nextLine();
                    System.out.print("Course: ");
                    user_course = sc.nextLine();
                    System.out.print("Year: ");
                    user_year = sc.nextInt();
                    sc.nextLine(); //Flushes the line
                    createUser(user_name, user_id, user_pass, user_contact, user_course, user_year);
                    break;
                case "3":
                    printUserList();
                    break;
                case "4":
                    System.out.println("EXITING...");
                    break;
                default:
                    System.out.println("INVALID INPUT!");
                    break;
            }

        } while (!input.equals("4"));
    }

    //This will run correctly if there is a valid id match inside the user list
    public void runSystem(String id, String pass){
        String input ="";
        User user = null;
        int user_index = 0;
        boolean valid = false;

        //Validate id and pass
        if(id.equals("ADMIN") && pass.equals("12345") && userList.isEmpty()){
            valid = true;
            createUser("ADMIN", "ADMIN", "12345", "-", "-", 0);
            //user = userList.getFirst();
        }

        for(User a : userList){
            if(a.getId().equals(id) && a.getPassword().equals(pass)){
                user = a;
                user_index = userList.indexOf(a);
                valid = true;
                break;
            }
        }



        if(!valid || user == null){
            System.out.println("USERNAME OR PASSWORD INCORRECT");
            return;
        }

        String user_name = user.getName().toUpperCase();
        //Access menu
        while(true){
            System.out.println("WELCOME, " + user_name);
            System.out.println("1 - REPORT A LOST ITEM");
            System.out.println("2 - REPORT A FOUND ITEM");
            System.out.println("3 - VIEW LOST ITEM LIST");
            System.out.println("4 - VIEW FOUND ITEM LIST");
            System.out.println("5 - EXIT");
            input = sc.nextLine();
            switch(input){
                case "1" :
                    System.out.println("YOU HAVE LOST AN ITEM AND WISH TO REPORT IT...");
                    lostList.add(reportItemLost(user));
                    break;
                case "2":
                    System.out.println("YOU HAVE FOUND AN ITEM AND WISH TO REPORT IT...");
                    lostList.add(reportItemFound(user));
                    break;
                case "3":
                    System.out.println("DISPLAYING " + user_name + "'S ITEMS LOST...");
                    user.displayLostList();
                    break;
                case "4":
                    System.out.println("DISPLAYING " + user_name + "'S ITEMS FOUND...");
                    System.out.println("The items here are yet to be claimed");
                    break;
                case "5":
                    System.out.println("EXITING...");
                    return;
                default:
                    System.out.println("INVALID INPUT!");
                    break;
            }
        }
    }
}
