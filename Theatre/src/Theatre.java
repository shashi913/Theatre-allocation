import java.util.*;
import java.io.*;

public class Theatre {

    private static int user_row_number_request;         // to get row booking number from the user
    private static int user_seat_number_request;         // to get seat booking number from the user
    private static int user_row_cancel_request;         // to get row canceling number from the user
    private static int user_seat_cancel_request;         // to get seat canceling number from the user
    static Scanner input = new Scanner(System.in);          //create the input object
    static ArrayList<Ticket> tickets = new ArrayList<>(48);       //create the ArrayList of tickets and object


    public static void main(String[] args) {
        System.out.println("\n\t\tWelcome to the New Theatre\n");
        int[] row1 = new int[12];              // row 1 with 12 seats
        int[] row2 = new int[16];              // row 2 with 16 seats
        int[] row3 = new int[20];              // row 3 with 20 seats
//        tickets = new ArrayList<Ticket>();

        int user_service_request = 100;         //for ask the user about what is the service he need


        do {
            try {
                System.out.print("""
                        -------------------------------------------------------------------------
                        please select an option:
                        1) Buy a ticket
                        2) Print seating area
                        3) Cancel ticket
                        4) List available seats
                        5) Save to file
                        6) Load from file
                        7) Print ticket information and total price
                        8) Sort tickets by price
                             0) Quit
                        -------------------------------------------------------------------------
                        Enter option:""");

                user_service_request = input.nextInt();         // take user service request

                switch (user_service_request) {             //giving the expression to switch case
                    case 1:
                        buy_ticket(row1, row2, row3);       // calling buy_ticket_method        ( user can buy a seat)
                        break;
                    case 2:
                        print_seating_area(row1, row2, row3);          // calling print_seating_area_method     (this print the seating area details)
                        break;
                    case 3:
                        cancel_ticket(row1, row2, row3);        //calling cancel_ticket_method  ( user can delete his seat)
                        break;
                    case 4:
                        show_available(row1, row2, row3);       //calling show available method (this shows seat numbers that are available)
                        break;
                    case 5:
                        save(row1, row2, row3);         //calling save method   (this save row details in to a file)
                        break;
                    case 6:
                        load(row1, row2, row3);         //calling load method    (this load row details from the file)
                        break;
                    case 7:
                        show_tickets_info();        // calling show_ticket_method ( this shows all information of the tickets)
                        break;
                    case 8:
                        bubble_sort();              // calling bubble_sort method    ( this will return a new list of Tickets sorted by Ticket price in ascending order)
                        break;
                    case 0:                             // if the user_input = 0 then stop the program
                        System.out.println("Thank you!");
                        break;
                    default:                            // if user input a wrong number this will display and loop again
                        System.out.println("Your input is not in options\nOptions are available only between 0 to 8\nplease select one of them");
                }

            } catch (Exception ex) {            //if try block false then comes here
                System.out.println("you input wrong data type!\nplease input your choice number correctly.");
                input.nextLine();               //move cursor to the next line
            }
        } while (user_service_request != 0);            // loop until user input 0
    }



    /*              Task 3            */
    public static void buy_ticket(int[] row1, int[] row2, int[] row3) {

        int row1_seat_available_count = row_available_seats(row1);           // check whether row1 seats available or not
        int row2_seat_available_count = row_available_seats(row2);           // check whether row2 seats available or not
        int row3_seat_available_count = row_available_seats(row3);           // check whether row3 seats available or not
        int total_seat_available_count = row1_seat_available_count + row2_seat_available_count + row3_seat_available_count;         //take total seats available
        if(total_seat_available_count < 0){                                             // if seats are not available
            System.out.println("sorry! No seat left\ncome another date");       //print this
            return;                                                                // then end the method
        } else {                                                                       // else
            System.out.println("There are "+ total_seat_available_count+ (total_seat_available_count>1 ?  " seats available" : "seat available"));             //print how many seats available and go to the next part
        }

        int price;          // this is for take the ticket price

        do {
            try {
                System.out.print("Enter your name: ");
                String name = new Scanner(System.in).next();              //enter person name

                System.out.print("Enter your Surname: ");
                String surname = new Scanner(System.in).next();             // enter person surname

                String email = get_email();                             //enter person email

                while (true) {
                    user_row_number_request = row_get_and_check();                      //take user row number
                    int[] row = row_select(row1, row2, row3, user_row_number_request);      // assigned the row according to the row number by using row_select method
                    int book_count = row_available_seats(row);                                     //get user selected row available seats by using row_available_seats
                    if (book_count < 0) {                                                 // check is there any seats left
                        System.out.println("This row doesn't have any seat left.\nplease select another row.");
                        continue;                       // if no seats available in that row go to the beginning of the loop again
                    } else{
                        System.out.println("This row only have " + (book_count) + (book_count >1 ?  " seats available" : "seat available"));
                    }

                    while (true) {
                        user_seat_number_request = seat_get_and_check( row);            // take_user_seat_request

                        if (row[user_seat_number_request - 1] != 0) {               // check whether seat is available if it not this part will happen and ask another seat
                            System.out.println("This seat is already booked.\nplease select another seat.\n");

                        } else {                            // if it available
                            row[user_seat_number_request - 1] = 1;
                            price = row_prices();               // take price by using row_price_method
                            break;                  //if user all data correct brake the loop
                        }
                    }
                    break;          //if row and seat  numbers are correct break the loop
                }

                tickets.add(new Ticket(user_row_number_request, user_seat_number_request, price, (new Person(name, surname, email))));          // enter the username ,surname email, row number, seat number and price into the ArrayList by using Ticket and Person classes
                System.out.println("Ticket booking successful!");
                break;

            } catch (Exception ex) {            // if invalid data type entered
                System.out.println("You Entered wrong data!\n please re-check and input again!");
                input.nextLine();
            }
        } while (true);
    }



    /*              Task 4              */
    public static void print_seating_area(int[] row1, int[] row2, int[] row3) {

        System.out.print("""
                    ***********
                    *  STAGE  *
                    ***********
                                            
                """);
        seat_type_print(row1); //print row1 seat areas by using seat_type_print method
        seat_type_print(row2); //print row2 seat areas by using seat_type_print method
        seat_type_print(row3); //print row3 seat areas by using seat_type_print method

    }



    /*              Task 5              */
    public static void cancel_ticket(int[] row1, int[] row2, int[] row3) {
        int booked_ticket_count = is_ticket_booked(row1) + is_ticket_booked(row2)+ is_ticket_booked(row3);  // check and get how many tickets are booked
         if(booked_ticket_count < 1){
            System.out.println("No tickets booked yet!");           // if there isn't any ticket booked yet display this and stop the method by return keyword
            return;
        }

        /*int*/
        user_row_cancel_request = row_get_and_check();          //take which row ticket user wants to cancel

        int[] row = row_select(row1, row2, row3, user_row_cancel_request);          //assign row name by using row_Select method
        /*int*/
        user_seat_cancel_request = seat_get_and_check( row);                    //take which seat user wants to cancel

        switch (user_row_cancel_request) {                  //giving the expression to switch case
            case 1:
                cancel_ticket_row_select(row1, user_seat_cancel_request);               //if expression == 1 then cancel row1
                break;

            case 2:
                cancel_ticket_row_select(row2, user_seat_cancel_request);               //if expression == 2 then cancel row2
                break;

            case 3:
                cancel_ticket_row_select(row3, user_seat_cancel_request);               //if expression == 3 then cancel row3
                break;
        }
    }



    /*              Task 6          */
    public static void show_available(int[] row1, int[] row2, int[] row3) {
        for(int j=0; j<3; j++){             // loop 3 times
            System.out.print("Seats available in row " + (j+1) + " : ");
            int[] row = row_select(row1, row2,row3, (j+1));         //select the row and assign it
            for (int i = 0; i < row.length; i++) {              // loop until all seats check is row
                if (row[i] == 0) {
                    System.out.print((i + 1) + ", ");               //if it nor a booked one print the number
                }
            }
            System.out.println("\b\b.");                    // after all printed this will remove last',' and space
        }
    }



    /*              Task 7          */
    public static void save(int[] row1, int[] row2, int[] row3) {
        try {
            FileWriter row_write = new FileWriter("Theatre Row Allocation.txt");              // create an object call row_writer to the file name "Theatre Row Allocation.txt"
            FileWriter ArrayList_write = new FileWriter("Theatre RowList Allocation.txt");          // create an object call ArrayList_writer to the file name "Theatre RowList Allocation.txt"

            array_save_to_file(row_write, row1);                    //save row1 information to the file by using array_save_to_file method
            array_save_to_file(row_write, row2);                    //save row2 information to the file by using array_save_to_file method
            array_save_to_file(row_write, row3);                    //save row3 information to the file by using array_save_to_file method
            ArrayList_save_to_file(ArrayList_write);                // save ArrayList information to the file by using ArrayList_save_to_file method
            row_write.close();                                      //close row_write object
            ArrayList_write.close();                                      //close ArrayList_write object
            System.out.println("file Successfully saved.");



        } catch (IOException ex) {
            System.out.println("An error occurred when files are saving!");
        }
    }



    /*              Task 8              */
    public static void load(int[] row1, int[] row2, int[] row3) {
        File file_array = new File("Theatre Row Allocation.txt");          //create a file_array object to TheaterSeatAllocation.txt file
        File file_ArrayList = new File("Theatre RowList Allocation.txt");          //create a file_ArrayList object to TheaterRowSeatAllocation.txt file

        try {
            if (file_array.exists() && file_ArrayList.exists()) {
                Scanner input_from_Array_file = new Scanner(file_array);                //create input_from_Array_file object to file_array file
                Scanner input_from_ArrayList_file = new Scanner(file_ArrayList);                //create input_from_ArrayList_file object to ArrayList file

                load_from_file_to_array(input_from_Array_file, row1);             //take row 1 information from the file by using load_from_file_to array method
                load_from_file_to_array(input_from_Array_file, row2);             //take row 2 information from the file by using load_from_file_to array method
                load_from_file_to_array(input_from_Array_file, row3);             //take row 3 information from the file by using load_from_file_to array method
                load_from_file_to_ArrayList(input_from_ArrayList_file);           //take ArrayList information from the file by using load_from_file_to_ArrayList method

                System.out.println("Successfully load from file.");

            } else {
                System.out.println("Files does not Exists!");
            }
        } catch (Exception ex) {
            System.out.println("Error occurred when files loading ");
        }
    }



    /*      Task 13     */
    public static void show_tickets_info() {

        if (tickets.size() > 0) {       //check whether ArrayList have any information
            int total_price = 0;        // to take the total price of the ticket
            for (int i = 0; i < tickets.size(); i++) {      // loop until going to all the indexes inside the ArrayList
//                Ticket ticket = new Ticket(tickets.get(i).row, tickets.get(i).seat, tickets.get(i).price, tickets.get(i).person);
                tickets.get(i).print();         // call the print method inside the Ticket class
                total_price += tickets.get(i).price;        //get the ticket price to the total price
            }
            System.out.println("\nTotal price: " + total_price);    //print the total prices of the tickets
        } else {
            System.out.println("There are no any saved tickets yet.");      //if ArrayList is empty this message will show
        }
    }



    /*      Task 14     */
    public static void bubble_sort() {
        int end = tickets.size() - 2;       //take the index of ArrayList ( last index - 1)
        Ticket swap_index_data;             //Keep the data temporary
        boolean continue_loop = true;       //for looping
        int total_price= 0;                 //take the total price of the tickets

        while (continue_loop) {
            continue_loop = false;          // if there is no swapping end the loop

            for (int i = 0; i <= end; i++) {        //loop until goes to the last index-1
                if (tickets.get(i).price > tickets.get(i + 1).price) {  //check whether index i price > index (i+1) price
                    swap_index_data = tickets.get(i);                   //if it larger temporary add data in to swap_index_data variable
                    tickets.set(i, tickets.get(i + 1));                 // if it larger swap index i+1 details to index i
                    tickets.set((i + 1), swap_index_data);        //  add the information inside the temporary variable in to the index i
                    continue_loop = true;       // loop again because there is a swap happened
                }
            }
            end--;    //after one time loop works and going to the next tern decrease the last index otherwise it gives an index out of bound error
        }
        for(int i= 0; i<tickets.size(); i++){
            tickets.get(i).print();         // call the print method inside the Ticket class
            total_price += tickets.get(i).price;
        }
        System.out.println("\nTotal price of the tickets: "+ total_price);
    }



    /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<   Building methods   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */

    /*   get the email  from user   */
    public static String get_email() {
        String email;
        do{
                System.out.print("Enter your email: ");
                email = new Scanner(System.in).next();              //take the email as string value
                if(!email.contains("@"))                   //check whether email contain a '@' mark
                    System.out.println("Invalid email address!. again input a valid one");
        } while (!email.contains("@"));         // loop until email contain a '@' mark
        return email;
    }



    /* get a row number and check whether requested row is a valid one */
    public static int row_get_and_check() {
        user_row_number_request = 100;                  //assigned a value before using inside the while loop
        while (user_row_number_request <= 0 || 3 < user_row_number_request) {           // if user_row_request is in between 1 and 3 stop loop
            try {
                System.out.print("input the row number : ");
                user_row_number_request = input.nextInt();                      // get user seat request
                if (user_row_number_request <= 0 || 3 < user_row_number_request) {         // if it in between the range
                    System.out.println("Invalid row number\nplease enter a valid row number\n");
                }
            } catch (Exception ex) {                                        // if wrong data type entered comes to this
                System.out.println("You entered a wrong data type.\nPlease check and re-enter.\n");
                input.nextLine();                   // move cursor to next line
            }
        }
        return user_row_number_request;             // return user_row_number_request
    }



    /* get a seat number and check whether requested seat is a valid one */
    public static int seat_get_and_check( int[] row) {
        do {
            try {
                System.out.print("Enter the seat number: ");
                user_seat_number_request = input.nextInt();     // take user seat request
                if (user_seat_number_request < 0 || user_seat_number_request > row.length) {        //check whether it is in range
                    System.out.println("Invalid seat number\nplease enter a valid seat number\n");
                }

            } catch (Exception ex) {
                System.out.println("You entered a wrong data type.\nPlease check and re-enter.\n");
                input.nextLine();
            }
        } while (user_seat_number_request <= 0 || user_seat_number_request > row.length);               //loop until valid seat number entered
        return user_seat_number_request;            ///return seat number
    }



/* this will use for assigned the row according to user request  */
    public static int[] row_select(int[] row1, int[] row2, int[] row3, int user_row_number_select) {
        switch (user_row_number_select) {
            case 1:
                return row1;
            case 2:
                return row2;
            default:
                return row3;
        }
    }



    /* This method is for take the ticket price from user and check whether it is higher than 0*/
    public static int row_prices(/*int user_row_number_request*/) {
        while (true) {
            try {
                System.out.print("Seat price: ");
                int seat_price = input.nextInt();
                if(seat_price > 0){         // check whether price higher than 0
                    return seat_price;      //if it higher than 0 go out from the method and return price
                } else{
                    System.out.println("price should be higher than 0");
                }
            } catch (Exception ex) {
                System.out.println("Wrong data type. re-enter");
                input.nextLine();
            }
        }
    }



/*      this method is for count whether there are any seats available or not          */
    public static int row_available_seats(int[] row/*, int[] row2, int[] row3*/){
        int available_count =0;                     // for cont the available seats
        for(int i: row){         //count row1 available seats
            if(row[i] == 0){                       //if seat is not booked one
                available_count++;                  // available_count increasing by one
            }
        }

        return available_count;                     // return the available_count
    }



    public static void seat_type_print(int[] row) {
        for (int l = 0; l < (10 - row.length / 2); l++) {
            System.out.print(" ");                  //print spaces before seats
        }
        for (int j = 0; j < row.length / 2; j++) {
            System.out.print(row[j] == 0 ? "O" : "X");              //print left side seats
        }
        System.out.print(" ");              //print the middle space
        for (int k = row.length / 2; k < row.length; k++) {         //print right side seats
            System.out.print(row[k] == 0 ? "O" : "X");
        }
        System.out.println();               //move cursor to new line
    }



    /* this method is for calculate is there any booked ticket */
    public static int is_ticket_booked(int[] row){
        int booked_ticket_count =0;
        for(int i=0; i<row.length; i++ ){
            if(row[i] == 1 ){
                booked_ticket_count++;
            }
        }
        return booked_ticket_count;
    }



/* this method uses for clear the seat booked inside the array*/
    public static void cancel_ticket_row_select(int[] row, int user_seat_cancel_req) {
        if (row[user_seat_cancel_req - 1] != 1) {           // if seat is not a booked one
            System.out.println("This seat is not booked. You must be making a mistake. Re-check");
        } else {
            row[user_seat_cancel_req - 1] = 0;          //if seat is a booked one mark it as not a booked onr
            clear_from_Array_list();       // call the clear_from_Array_List function to clear the details inside the ArrayList according to user request
            System.out.println("Ticket successfully canceled");
        }
    }



    /*this method is for clear the data inside the tickets ArrayList */
    public static void clear_from_Array_list() {
        for (int i = 0; i < tickets.size(); i++) {          // this will loop until details are found
            if (tickets.get(i).row == user_row_cancel_request) {        // this will check whether row is matches with the details
                if (tickets.get(i).seat == user_seat_cancel_request) {        // this will check whether seat is matches with the details
                    tickets.remove(i);                      // if it matches clear the details in that index
                    break;      // go out from the for loop
                }
            }
        }
    }



    /* This method is for save the ArrayList data in to a file*/
     public static void ArrayList_save_to_file(FileWriter write_to_file){
         try{
             for(int i = 0; i < tickets.size(); i++){           //loop until last index of the tickets ArrayList
                 String details = tickets.get(i).to_string();       // get all details as string by using to_string method inside the Ticket class
                 write_to_file.write(details);                      // write details to the file
                 write_to_file.write("\n");                     // print new line
             }

         }catch(IOException ex){
             System.out.println("Error occurred when saving to ArrayList file ");
         }
     }



    /* This method is for save the array data in to the file */
    public static void array_save_to_file(FileWriter write_to_file, int[] row) {
        try {
            for (int i=0; i< row.length;i++) {      //loop until go to last seat number
                write_to_file.write(row[i] + " ");      // write the seat information separated by spaces
            }
            write_to_file.write("\n");              //after one row printed this will get the curses to a new line

        } catch (IOException ex) {
            System.out.println("Error occurred when saving to Array file ");
        }
    }



    /* This method is for take the ArrayList information from the file */
    public static void load_from_file_to_ArrayList(Scanner input_from_ArrayList_file){
        tickets.clear();
        int i=0;
        while(input_from_ArrayList_file.hasNextLine()){                 // loop until file has a new data line
            String name = input_from_ArrayList_file.next();             // get name as a string from file
            String surname = input_from_ArrayList_file.next();             // get surname as a string from file
            String email = input_from_ArrayList_file.next();             // get email as a string from file
            String row = input_from_ArrayList_file.next();             // get row as a string from file
            String seat = input_from_ArrayList_file.next();             // get seat as a string from file
            String price = input_from_ArrayList_file.next();             // get price as a string from file
            tickets.add(i, new Ticket((Integer.parseInt(row))/* convert string to an int*/, Integer.parseInt(seat)/* convert string to an int*/, Integer.parseInt(price)/* convert string to an int*/, (new Person(name, surname, email))));          // enter the username ,surname email, row number, seat number and price into the ArrayList by using Ticket and Person classes
            input_from_ArrayList_file.nextLine();
            i++;
        }
    }



    /* this method is for take the array information from the file */
    public static void load_from_file_to_array(Scanner input_from_file, int[] row) {
        for (int i = 0; i < row.length; i++) {          //loop until i= row.length -1
            row[i] = input_from_file.nextInt();         //take the information one by one separating spaces
        }
        input_from_file.nextLine();                     //move the curses to next line of the file
    }
}