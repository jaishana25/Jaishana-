package Banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class sample {

    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/sample";
            String uname = "root";
            String password = "Orcl@1234";

            Connection conn = DriverManager.getConnection(url, uname, password);
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter username: ");
            String user = scanner.nextLine();
            System.out.print("Enter password: ");
            String pass = scanner.nextLine();

            String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement loginStmt = conn.prepareStatement(loginQuery);
            loginStmt.setString(1, user);
            loginStmt.setString(2, pass);
            ResultSet rs = loginStmt.executeQuery();

            if (rs.next()) {
                boolean running = true;
                while (running) {
                    System.out.println("\n1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Check Balance");
                    System.out.println("4. Edit Profile");
                    System.out.println("5. Change Password");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {
                        System.out.print("Enter deposit amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        String depositQuery = "UPDATE users SET balance = balance + ? WHERE username = ?";
                        PreparedStatement depositStmt = conn.prepareStatement(depositQuery);
                        depositStmt.setDouble(1, amount);
                        depositStmt.setString(2, user);
                        depositStmt.executeUpdate();
                        System.out.println("Deposit successful.");
                    }

                    else if (choice == 2) {
                        System.out.print("Enter withdrawal amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        String balanceQuery = "SELECT balance FROM users WHERE username = ?";
                        PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery);
                        balanceStmt.setString(1, user);
                        ResultSet balanceRs = balanceStmt.executeQuery();

                        if (balanceRs.next()) {
                            double balance = balanceRs.getDouble("balance");
                            if (balance >= amount) {
                                String withdrawQuery = "UPDATE users SET balance = balance - ? WHERE username = ?";
                                PreparedStatement withdrawStmt = conn.prepareStatement(withdrawQuery);
                                withdrawStmt.setDouble(1, amount);
                                withdrawStmt.setString(2, user);
                                withdrawStmt.executeUpdate();
                                System.out.println("Withdrawal successful.");
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        }
                    }

                    else if (choice == 3) {
                        String balanceQuery = "SELECT balance FROM users WHERE username = ?";
                        PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery);
                        balanceStmt.setString(1, user);
                        ResultSet balanceRs = balanceStmt.executeQuery();

                        if (balanceRs.next()) {
                            System.out.println("Current Balance: ₹" + balanceRs.getDouble("balance"));
                        }
                    }

                    else if (choice == 4) {
                        System.out.println("Edit Profile:");
                        System.out.println("1. Name");
                        System.out.println("2. Address");
                        System.out.println("3. Email");
                        System.out.println("4. Phone");
                        System.out.print("Choose field: ");
                        int fieldChoice = scanner.nextInt();
                        scanner.nextLine();

                        String field = null;
                        if (fieldChoice == 1) field = "name";
                        else if (fieldChoice == 2) field = "address";
                        else if (fieldChoice == 3) field = "email";
                        else if (fieldChoice == 4) field = "phone";

                        if (field != null) {
                            System.out.print("Enter new " + field + ": ");
                            String newValue = scanner.nextLine();
                            String updateQuery = "UPDATE users SET " + field + " = ? WHERE username = ?";
                            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                            updateStmt.setString(1, newValue);
                            updateStmt.setString(2, user);
                            updateStmt.executeUpdate();
                            System.out.println(field + " updated.");
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }

                    else if (choice == 5) {
                        System.out.print("Enter current password: ");
                        String oldPass = scanner.nextLine();
                        System.out.print("Enter new password: ");
                        String newPass = scanner.nextLine();

                        String updatePassQuery = "UPDATE users SET password = ? WHERE username = ? AND password = ?";
                        PreparedStatement passStmt = conn.prepareStatement(updatePassQuery);
                        passStmt.setString(1, newPass);
                        passStmt.setString(2, user);
                        passStmt.setString(3, oldPass);
                        int rows = passStmt.executeUpdate();

                        if (rows > 0) {
                            System.out.println("Password changed.");
                        } else {
                            System.out.println("Incorrect current password.");
                        }
                    }

                    else if (choice == 6) {
                        running = false;
                    }

                    else {
                        System.out.println("Invalid option.");
                    }

                    if (running) {
                        System.out.print("Do you want to continue? (y/n): ");
                        String cont = scanner.nextLine();
                        if (!cont.equalsIgnoreCase("y")) {
                            running = false;
                        }
                    }
                }
            } else {
                System.out.println("Login failed.");
            }

            conn.close();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
