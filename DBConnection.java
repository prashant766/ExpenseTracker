package ExpenseTracker;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1\r\n";
    private static final String USER = "HR";
    private static final String PASS = "hr";  // change if needed

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}
