/*      Task 10     */
class Ticket {
    int row;        // instant row variable
    int seat;       // instant seat variable
    int price;      // instant price variable
    Person person;      //Person class variable


    /*      constructor in Ticket class */
     public Ticket(int row_request, int seat_request, int total_price, Person person){
        this.row = row_request;
        this.seat = seat_request;
        this.price= total_price;
        this.person = person;
    }


    /*      Task 11     */
    void print(){
        System.out.println("New Ticket\n");
        System.out.println("Person name    : "+ person.name);
        System.out.println("Person surname : "+ person.Surname);
        System.out.println("Person email   : "+ person.email);
        System.out.println("row number     : "+ row);
        System.out.println("Seat number    : "+ seat);
        System.out.println("price          : "+ price);
        System.out.println();
    }



    /* get all data as a String in to a one variable */
    String to_string(){
        String name = person.name + " "+ person.Surname+" "+ person.email +" "+row+ " "+ seat+ " "+ price;
        return name;
    }
}
