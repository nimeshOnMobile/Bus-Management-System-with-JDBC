import java.sql.*;
import java.util.*;

class BusOperations{
    Scanner inp;

    public BusOperations(Scanner inp){
        this.inp = inp;
    }

    public void addNewbus(){
        System.out.println("Enter bus capacity, source, destination and timing (HH:MMAM/PM-HH:MMAM/PM) and price");
        int capacity = inp.nextInt();
        inp.nextLine(); //Consume the newline character
        String src = inp.nextLine();
        String dest = inp.nextLine();
        String timing = inp.nextLine();
        double price = inp.nextDouble();
        inp.nextLine(); //Consume the newline character
        try{
            String query = "INSERT INTO buses (capacity, src, dest, timing, price) VALUES (?, ?, ?, ?, ?)";
            Connection con = DatabaseHelper.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, capacity);
            ps.setString(2, src);
            ps.setString(3, dest);
            ps.setString(4, timing);
            ps.setDouble(5, price);
            ps.executeUpdate();

            System.out.println("Bus has been successfully added");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void updateBusTimings(){
        System.out.println("Enter the bus Id you want to update:");
        int id = inp.nextInt();
        inp.nextLine(); //Consume the newline character

        System.out.println("Enter the new timings");
        String timing = inp.nextLine();

        try{
            String query = "UPDATE buses SET timing = ? WHERE id = ?";
            Connection con = DatabaseHelper.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, timing);
            ps.setInt(2, id);
            ps.executeUpdate();

            System.out.println("Bus Details have been successfully updated");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void removeBus(){
        System.out.println("Enter the Bus ID to remove");
        int id = inp.nextInt();
        inp.nextLine(); //Consume the newline character
        
        try{
            String query = "DELETE FROM buses WHERE id = ?";
            Connection con = DatabaseHelper.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Bus has been successfully removed");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

public class BusServiceManager{
    public static void main(String[] args){
        Scanner inp = new Scanner(System.in);
        BusOperations busOperations = new BusOperations(inp);

        System.out.println("Do you want to Add new bus, Update timings of existing bus or Remove a bus");
        String reply = inp.nextLine();

        switch(reply){
            case "Add":
                busOperations.addNewbus();
                break;
            case "Update":
                busOperations.updateBusTimings();
                break;
            case "Remove":
                busOperations.removeBus();
                break;    
            default:
                System.out.println("Invalid action was selected");        
        }
    }
}

//java -cp ".;C:/Users/nimesh.thakur/Documents/Java/mariadb-java-client-3.3.2.jar" BusServiceManager