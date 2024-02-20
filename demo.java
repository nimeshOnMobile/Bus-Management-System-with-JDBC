import java.util.*;
import java.sql.*;

class Bus {
    int capacity;
    String src;
    String dest;
    String timing; //"09:30AM-12:30PM"

    synchronized int getCapacity() {
        return this.capacity;
    }

    synchronized void buyTicket(int busId) {
       Connection con;
       PreparedStatement ps;
       ResultSet rs;
       try{
        con = DatabaseHelper.getConnection();
        String query = "SELECT capacity FROM buses WHERE id = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1, busId);
        rs = ps.executeQuery();

        int currentCapacity = 0;
        if(rs.next()){
            currentCapacity = rs.getInt("capacity");
        }

        if(currentCapacity > 0){
            String updateQuery = "UPDATE buses SET capacity = capacity - 1 WHERE id = ?";
            ps = con.prepareStatement(updateQuery);
            ps.setInt(1, busId);
            ps.executeUpdate();

            String priceQuery = "SELECT price FROM buses WHERE id = ?";
            ps = con.prepareStatement(priceQuery);
            ps.setInt(1,busId);
            rs = ps.executeQuery();

            double price = 0.0;
            if(rs.next()){
                price = rs.getDouble("price");
            }

            String insertTicketQuery = "INSERT INTO tickets(bus_id, status, amount_paid) VALUES (?, 'booked', ?)";
            ps = con.prepareStatement(insertTicketQuery);
            ps.setInt(1, busId);
            ps.setDouble(2, price);
            ps.executeUpdate();

            System.out.println("The ticket has been booked for Rs " + price);
        }
        else{
            System.out.println("Sorry, The bus is already full.");
        }
       }
       catch(SQLException e){
        System.out.println(e);
       }
       
    }

    synchronized void cancelTicket(int ticketId) {
       Connection con;
       PreparedStatement ps;
       ResultSet rs;
       try{
        con = DatabaseHelper.getConnection();
        String query = "SELECT bus_id FROM tickets WHERE ticket_id = ? AND status = 'booked'";
        ps = con.prepareStatement(query);
        ps.setInt(1, ticketId);
        rs = ps.executeQuery();
        if(!rs.next()){
            System.out.println("Ticket not found");
            return;
        }

        int busId = rs.getInt("bus_id");

        //Now as I already know that there does exist a ticket with a particular ticketId
        //So I will try to update the status of the ticket to cancelled

        query = "UPDATE tickets SET status = 'cancelled' WHERE ticket_id = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1,ticketId);
        ps.executeUpdate();

        //After we have updated the status to cancelled, we would have to also increase
        //capacity of bus by 1

        query = "UPDATE buses SET capacity = capacity + 1 WHERE id = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1, busId);
        ps.executeUpdate();

        System.out.println("The ticket has been successfully cancelled.");
       }
       catch(SQLException e){
        System.out.println(e);
       }
    }
}

class Consumer implements Runnable {
    Scanner inp;

    Consumer(Scanner inp) {
        this.inp = inp;
    }


    public void run() {
       try{
        System.out.println("Do you want to book the Ticket or Cancel the Ticket?");
        String reply = inp.nextLine();
        if(reply.equals("Book")){
            System.out.println("Enter bus ID to book the ticket");
            int busId = inp.nextInt();
            inp.nextLine(); // Consume the newline character
            bookTicket(busId);
        }
        else if(reply.equals("Cancel")){
            System.out.println("Enter ticket ID to cancel");
            int ticketId = inp.nextInt();
            inp.nextLine(); // Consume the newline character
            cancelTicket(ticketId);
        }
        else{
            System.out.println("Invalid Command");
        }
       }
       catch(Exception e){
        System.out.println(e);
       }

       System.out.println("");
    }

    private void bookTicket(int busId) {
        new Bus().buyTicket(busId);
    }

    private void cancelTicket(int ticketId) {
        new Bus().cancelTicket(ticketId);
    }
}

public class demo {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);

        ArrayList<Integer> busIds = new ArrayList<Integer>();
        try{
            Connection con = DatabaseHelper.getConnection();
            Statement st = con.createStatement();
            String query = "SELECT id FROM buses";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                busIds.add(rs.getInt("id"));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        System.out.println("These are the buses that we have available");
        displayBusDetails();

        int m;
        System.out.println("How many operations do you want to perform?");
        m = inp.nextInt();
        inp.nextLine(); // Consume the newline character

        Thread[] threads = new Thread[m];

        for(int i=0;i<m;i++){
            Consumer consumer = new Consumer(inp);
            threads[i] = new Thread(consumer);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < m; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Exception has occured " + e);
            }
        }

        displayBusDetails();
    }

    static void displayBusDetails() {
        Connection con;
        Statement st;
        ResultSet rs;

        try{
            con = DatabaseHelper.getConnection();
            st = con.createStatement();
            String query = "SELECT * FROM buses";
            rs = st.executeQuery(query);

            while(rs.next()){
                int id = rs.getInt("id");
                int capacity = rs.getInt("capacity");
                String src = rs.getString("src");
                String dest = rs.getString("dest");
                String timing = rs.getString("timing");

                System.out.println("Bus ID " + id + "->");
                System.out.println("Source: " + src);
                System.out.println("Destination: " + dest);
                System.out.println("Timings: " + timing);
                System.out.println("Capacity: " + capacity);
                System.out.println();
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
}

//Implement MultiThreading
//Seperate Consumer part and Bus Part
//Connection with MariaDB
//jdbc, jpa, hibernate
//javac demo.java
//java -cp ".;C:/Users/nimesh.thakur/Documents/Java/mariadb-java-client-3.3.2.jar" demo