package ExpenseTracker;

import java.sql.*;
import java.util.Scanner;
public class Main implements ShowService {

    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main obj = new Main();
        obj.menu();
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== EXPENSE TRACKER ====");
            System.out.println("1. ADD EXPENSE");
            System.out.println("2. VIEW EXPENSES");
            System.out.println("3. VIEW TOTAL EXPENSE");
            System.out.println("4. EXIT");
            System.out.print("ENTER YOUR CHOICE: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpense();
                    break;
                case 3:
                    totalExpense();
                    break;
                case 4:
                    System.out.println("THANK YOU EXITING.....");
                    return;
                default:
                    System.out.println("INVALID CHOICE");
            }
        }
    }

    // ✅ INSERT INTO DB
    @Override
    public void addExpense() {
        System.out.print("ENTER ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("ENTER TITLE: ");
        String title = sc.nextLine();

        System.out.print("ENTER AMOUNT: ");
        double amt = sc.nextDouble();
        sc.nextLine();

        System.out.print("ENTER DATE (dd-mm-yy): ");
        String date = sc.nextLine();

        String sql = "INSERT INTO expenses (id, title, amount, expense_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setDouble(3, amt);
            ps.setString(4, date);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ EXPENSE ADDED SUCCESSFULLY");
            } else {
                System.out.println("❌ EXPENSE NOT ADDED");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ ID already exists! Use unique ID.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ SELECT FROM DB
    @Override
    public void viewExpense() {

    	String sql = "SELECT id, title, amount, expense_date FROM expenses ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nID | TITLE | AMOUNT | DATE");
            System.out.println("-------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;

                int id = rs.getInt("id");
                String title = rs.getString("title");
                double amount = rs.getDouble("amount");
                String date = rs.getString("expense_date");


                System.out.println(id + " | " + title + " | " + amount + " | " + date);
            }

            if (!found) {
                System.out.println("NO RECORD FOUND !!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ SUM FROM DB
    @Override
    public void totalExpense() {

        String sql = "SELECT SUM(amount) AS total FROM expenses";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                double total = rs.getDouble("total");
                System.out.println("✅ TOTAL EXPENSE = " + total);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
