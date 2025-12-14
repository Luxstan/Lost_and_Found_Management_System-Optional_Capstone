import ItemPack.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LFSystem {
    Scanner sc = new Scanner(System.in);
    public ArrayList<User> userList;
    public ArrayList<Item> lostList;
    public ArrayList<Item> foundList;
    public ArrayList<Item> historyList; //MIGHT NOT NEED THIS

    User current_user = null;
    int user_index = 0;
    boolean valid = false;
    public int item_id = 1;

    public LFSystem(){
        lostList = new ArrayList<>();
        foundList = new ArrayList<>();
        historyList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    //Methods
    /*public void addLostItem(Item a){}
    public void addFoundItem(Item a){}
    public void searchItem(Item a){}*/

    public void createUser(String name, String id, String pass, String contactNo, String course, int year){
        //First looks if it is not a duplicate. Returns and does nothing if it is a dupe.
        for(User a : userList){
            if(a.getId().equals(id) && a.getPassword().equals(pass)){
                return;
            }
        }

        //If not, then the user will be created.
        User user = new User(name, id, pass, contactNo, course, year);
        userList.add(user);
        System.out.println("User created: " + name + " " + id + " " + pass + " " + contactNo + " " + course + "-" + year);
    }

    public void printUserList(){
        System.out.println("There are " + userList.size() + " user/s");
        System.out.println("Printing the user list...");
        for(User a : userList){
            System.out.println(a.getId() + ", " + a.getName() + ", " + a.getContactNo() + ", " + a.getCourse() + "-" + a.getYear());
        }
        return;
    }

    public Item reportItem(User a, String option){
        Item item = null;
        //inputs
        String input;

        //General Fields shared across many items
        String brand, material, type, color;

        do {
            String name, description, location;
            System.out.print("ITEM NAME: ");
            name = sc.nextLine();
            System.out.print("DESCRIBE THE ITEM: ");
            description = sc.nextLine();
            System.out.print("LAST LOCATION SEEN: ");
            location = sc.nextLine();

            System.out.println("WHICH ITEM TYPE FITS THE MOST:");
            System.out.println("1 - MONEY");
            System.out.println("2 - DOCUMENT");
            System.out.println("3 - ACCESSORY");
            System.out.println("4 - BAG");
            System.out.println("5 - CLOTHING");
            System.out.println("6 - ELECTRONIC");
            System.out.println("7 - FOOD CONTAINER");
            System.out.println("8 - OTHER");
            System.out.println("X - EXIT");
            input = sc.nextLine();
            switch (input) {
                case "1":
                    String inWallet;
                    double d_input;
                    System.out.println("CHOSEN ITEM TYPE: MONEY");
                    System.out.print("AMOUNT: ");
                    d_input = sc.nextDouble();
                    System.out.print("FOUND IN WALLET? Y/N: ");
                    sc.nextLine();
                    inWallet = sc.nextLine();
                    if(inWallet.equals("Y") || inWallet.equals("y")){
                        item = new Money(d_input, true); //AMOUNT, inWALLET?
                    } else if (inWallet.equals("N") || inWallet.equals("n")) {
                        item = new Money(d_input, false); //AMOUNT, inWALLET?
                    }
                    else{
                        System.out.println("INVALID INPUT!");
                    }

                    break;
                case "2":
                    String ownerName, ownerID = "";
                    System.out.println("CHOSEN ITEM TYPE: DOCUMENT");
                    System.out.print("OWNER NAME [if illegible]: ");
                    ownerName = sc.nextLine();
                    System.out.print("OWNER'S ID [if illegible]: ");
                    ownerID = sc.nextLine();
                    System.out.print("DOCUMENT TYPE [ID, CERTIFICATES...]: ");
                    type = sc.nextLine();
                    item = new Document(ownerName, ownerID, type);
                    break;
                case "3":
                    System.out.println("CHOSEN ITEM TYPE: ACCESSORY");
                    System.out.print("MATERIAL: ");
                    material = sc.nextLine();
                    System.out.print("TYPE: ");
                    type = sc.nextLine();
                    System.out.print("COLOR: ");
                    color = sc.nextLine();
                    item = new Accessory(material, type, color);
                    break;
                case "4":
                    System.out.println("CHOSEN ITEM TYPE: BAG");
                    System.out.print("BRAND: ");
                    brand = sc.nextLine();
                    System.out.print("MATERIAL: ");
                    material = sc.nextLine();
                    System.out.print("TYPE: ");
                    type = sc.nextLine();
                    System.out.print("COLOR: ");
                    color = sc.nextLine();
                    item = new Bag(brand, material, type, color);
                    break;
                case "5":
                    String size;
                    System.out.println("CHOSEN ITEM TYPE: CLOTHING");
                    System.out.print("SIZE: ");
                    size = sc.nextLine();
                    System.out.print("BRAND: ");
                    brand = sc.nextLine();
                    System.out.print("MATERIAL: ");
                    material = sc.nextLine();
                    System.out.print("TYPE: ");
                    type = sc.nextLine();
                    System.out.print("COLOR: ");
                    color = sc.nextLine();
                    item = new Clothing(size, brand, material, type, color);
                    break;
                case "6":
                    String model, isWearable;
                    System.out.println("CHOSEN ITEM TYPE: ELECTRONIC");
                    System.out.print("MODEL: ");
                    model = sc.nextLine();
                    System.out.print("BRAND: ");
                    brand = sc.nextLine();
                    System.out.print("MATERIAL: ");
                    material = sc.nextLine();

                    System.out.print("IS THIS GADGET WEARABLE? [Smart Watch, Headphones...] [Y/N]: ");
                    isWearable = sc.nextLine();

                    System.out.print("TYPE: ");
                    type = sc.nextLine();
                    System.out.print("COLOR: ");
                    color = sc.nextLine();

                    if(isWearable.equals("Y") || isWearable.equals("y")){
                        item = new WearableElectronic(model, brand, material, type, color);
                    }
                    else if(isWearable.equals("N") || isWearable.equals("n")){
                        item = new Electronic(model, brand, material, type, color);
                    }
                    break;
                case "7":
                    String capacity;
                    System.out.println("CHOSEN ITEM TYPE: FOOD CONTAINER");
                    System.out.print("CAPACITY: ");
                    capacity = sc.nextLine();
                    System.out.print("BRAND: ");
                    brand = sc.nextLine();
                    System.out.print("TYPE: ");
                    type = sc.nextLine();
                    System.out.print("COLOR: ");
                    color = sc.nextLine();

                    item = new FoodContainer(capacity, brand, type, color);
                    break;
                case "8":
                    System.out.println("CHOSEN ITEM TYPE: OTHER - MISCELLANEOUS");
                    String itemCat;
                    System.out.print("WHAT ITEM CATEGORY DO YOU THINK THIS ITEM BELONGS IN: ");
                    itemCat = sc.nextLine();
                    item = new Miscellaneous(itemCat);
                    break;
                case "X":
                    System.out.println("EXITING...");
                    break;
                default:
                    System.out.println("INVALID INPUT!");
                    break;
            }
            if(item!=null){
                item.setItemName(name);
                item.setDetails(description);
                item.setLastLocationSeen(location);

                if(option.equals("REPORT LOST")){
                    a.addLostItem(item);
                    item.setItemID(item_id++);
                    System.out.println("ADDED AN ITEM TO LOST LIST");
                } else if (option.equals("REPORT FOUND")) {
                    a.addFoundItem(item);
                    item.setItemID(item_id++);
                    System.out.println("ADDED AN ITEM TO FOUND LIST");
                }
                else{
                    System.out.println("ERROR OPTION IS WRONG");
                }
            }
            item = null;

            System.out.print("Do you wish to report another item? [Y/N]: ");
            input = sc.nextLine();
            if(input.equals("N")){
                input = "X";
            }
        } while (!input.equals("X"));
        return null;
    }

    void handleReporting(){

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

    public boolean validateUser(String id, String pass){
        for(User a : userList){
            if(a.getId().equals(id) && a.getPassword().equals(pass)){
                current_user = a;
                user_index = userList.indexOf(a);
                valid = true;
                break;
            }
        }
        return valid;
    }

    //This will run correctly if there is a valid id match inside the user list
    public void runSystem(String id, String pass){
        String input ="";


        //Validate id and pass
        if(id.equals("ADMIN") && pass.equals("12345") && userList.isEmpty()){
            valid = true;
            createUser("ADMIN", "ADMIN", "12345", "-", "-", 0);
            //user = userList.getFirst();
        }

        for(User a : userList){
            if(a.getId().equals(id) && a.getPassword().equals(pass)){
                current_user = a;
                user_index = userList.indexOf(a);
                valid = true;
                break;
            }
        }

        if(!valid || current_user == null){
            System.out.println("USERNAME OR PASSWORD INCORRECT");
            return;
        }

        String user_name = current_user.getName().toUpperCase();
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
                    lostList.add(reportItem(current_user, "REPORT LOST"));
                    break;
                case "2":
                    System.out.println("YOU HAVE FOUND AN ITEM AND WISH TO REPORT IT...");
                    lostList.add(reportItem(current_user, "REPORT FOUND"));
                    break;
                case "3":
                    System.out.println("DISPLAYING " + user_name + "'S ITEMS LOST...");
                    current_user.displayList("LOST");
                    break;
                case "4":
                    System.out.println("DISPLAYING " + user_name + "'S ITEMS FOUND...");
                    System.out.println("The items here are yet to be claimed");
                    current_user.displayList("FOUND");
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

    public void encodeUsersFromFile(){ //This method creates [new] users based from the csv file. Acts like a "load".
        String[] user_details;
        String curr;
        try (BufferedReader br = new BufferedReader(new FileReader("records.csv"))) {
            while ((curr = br.readLine()) != null) {
                user_details = curr.split(",");
                String[] courseAndYear = user_details[4].split("-");
                String course = courseAndYear[0];
                int year = Integer.parseInt(courseAndYear[1]);
                createUser(user_details[0], user_details[2], user_details[1], user_details[3], course, year);
            }
        } catch (IOException ignored) {}
    }

    public String displayUserLostList(){
        //current_user.displayList("LOST");
        return current_user.outputItemName("LOST");
    }

    public String displayUserFoundList(){
        //current_user.displayList("FOUND");
        return current_user.outputItemName("FOUND");
    }

    public void createItem(String status, String item_name, String details, String lastSeenAt, String reportedBy, int category){
        System.out.println(category);
        /*
        There are 0-9 Categories in the combo box
        Accessory
        Bag
        Clothing
        Document
        Electronic
        Food Container
        Money
        Tumbler
        Others
        */
        Item a = null;
        switch(category){
            case 1:
                a = new Accessory();
            case 2:
                a = new Bag();
            case 3:
                a = new Clothing();
            case 4:
                a = new Document();
            case 5:
                a = new Electronic();
            case 6:
                a = new FoodContainer();
            case 7:
                a = new Money();
            case 8:
                a = new FoodContainer();
            case 9:
                a = new Miscellaneous();
            default:
                break;
        }
        if(a == null){
            System.out.println("ERROR. ITEM IS NULL.");
            return;
        }
        a.setItemID(item_id);
        a.setItemName(item_name);
        a.setDetails(details);
        a.setLastLocationSeen(lastSeenAt);
        if(status.equals("Lost")){
            current_user.addLostItem(a);
        }
        else if(status.equals("Found")){
            a.setFoundBy(reportedBy);
            current_user.addFoundItem(a);
        }


    }
}
