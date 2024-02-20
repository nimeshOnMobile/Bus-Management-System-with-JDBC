import java.sql.*;

public class DatabaseHelper {
    private static final String DATABASE_URL = "jdbc:mariadb://localhost:3306/BusReservation";
    private static final String DATABASE_USER = "nimesh";
    private static final String DATABASE_PASSWORD = "YjmG974$NST";

    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}