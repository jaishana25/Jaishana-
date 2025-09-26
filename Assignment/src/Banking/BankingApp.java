package Banking;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			String url = "jdbc:mysql://localhost:3306/sample";
			String uname="root";
			String password = "Orcl@1234";
			Connection con= DriverManager.getConnection(url,uname,password);
//			System.out.println("Connected");
			Scanner scanner = new Scanner(System.in);
			
			
			System.out.println("Welcome to JBP Banking Services ");

            System.out.print("Enter username: ");
            String user = scanner.nextLine();
            System.out.print("Enter password: ");
            String pass = scanner.nextLine();
            
            String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            
            PreparedStatement login = con.prepareStatement(loginQuery);
            login.setString(1, user);
            login.setString(2, pass);
            ResultSet rs = login.executeQuery();
            
            if (rs.next()){
                boolean c = true;
            	while(c) {
            		System.out.println("\n 1.Deposit");
            		System.out.println(" 2.Withdraw");
            		System.out.println("3.Check Balance");
            		System.out.println("4.Edit Profile");
            		System.out.println("5.Change Password");
            		System.out.println("6.EXIT");
            		System.out.println("Enter your choice: ");
            		int choice = scanner.nextInt();
            		scanner.nextLine();
            		
            	    
            		if (choice == 1) {
            			System.out.println("Enter deposit amount: ");
            			int amount = scanner.nextInt();
            			scanner.nextLine();
            			
            			 String depositQuery = "UPDATE users SET balance = balance + ? WHERE username = ?";
                         PreparedStatement deposit = con.prepareStatement(depositQuery);
                         deposit.setDouble(1, amount);
                         deposit.setString(2, user);
                         deposit.executeUpdate();
                         System.out.println("Deposit successful.");
                 
            			
                    	
                    }
            		
            		else if (choice == 2) {
            			
            			System.out.print("Enter withdrawal amount: ");
                        int amount = scanner.nextInt();
                        scanner.nextLine();

                        String balanceQuery = "SELECT balance FROM users WHERE username = ?";
                        PreparedStatement balanceStmt = con.prepareStatement(balanceQuery);
                        balanceStmt.setString(1, user);
                        ResultSet balanceRs = balanceStmt.executeQuery();

                        if (balanceRs.next()) {
                            int balance = balanceRs.getInt("balance");
                            if (balance >= amount) {
                                String withdrawQuery = "UPDATE users SET balance = balance - ? WHERE username = ?";
                                PreparedStatement withdraw = con.prepareStatement(withdrawQuery);
                                withdraw.setInt(1, amount);
                                withdraw.setString(2, user);
                                withdraw.executeUpdate();
                                System.out.println("Withdrawal successful.");
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        }
            		}
            		

                    else if (choice == 3) {
                        String balanceQuery = "SELECT balance FROM users WHERE username = ?";
                        PreparedStatement balance = con.prepareStatement(balanceQuery);
                        balance.setString(1, user);
                        ResultSet balanceRs = balance.executeQuery();

                        if (balanceRs.next()) {
                            System.out.println("Current Balance: ₹" + balanceRs.getInt("balance"));
                        }
                    }

                    else if (choice == 4) {
                        System.out.println("Edit Profile:");
                        System.out.println("1. Name");
                        System.out.println("2. Address");
                        System.out.println("3. Email");
                        System.out.println("4. Phone");
                        System.out.print("Choose field: ");
                        int profileChoice = scanner.nextInt();
                        scanner.nextLine();

                        String field = null;
                        if (profileChoice == 1) field = "name";
                        else if (profileChoice == 2) field = "address";
                        else if (profileChoice == 3) field = "email";
                        else if (profileChoice == 4) field = "phone";

                        if (field != null) {
                            System.out.print("Enter new " + field + ": ");
                            String newValue = scanner.nextLine();
                            String updateQuery = "UPDATE users SET " + field + " = ? WHERE username = ?";
                            PreparedStatement update = con.prepareStatement(updateQuery);
                            update.setString(1, newValue);
                            update.setString(2, user);
                            update.executeUpdate();
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
                        PreparedStatement passStmt = con.prepareStatement(updatePassQuery);
                        passStmt.setString(1, newPass);
                        passStmt.setString(2, user);
                        passStmt.setString(3, oldPass);
                       
                        int rows = passStmt.executeUpdate();

                        
                        if (rows > 0) {
                            System.out.println("Password changed.");
                        } else {
                            System.out.println("Incorrect password.");
                        }
                    }

                    else if (choice == 6) {
                    	
                       c = false;
                       System.out.println("Thankyou for using me");
                    }

                    else {
                        System.out.println("Invalid option.");
                    }

                    if (c) {
                        System.out.print("Do you want to continue? (y/n): ");
                        String cont = scanner.nextLine();
                        if (!cont.equalsIgnoreCase("y")) {
                            c = false;
                        }
                    }
                }
            } else {
                System.out.println("Login failed.");
            }

            con.close();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

            	}
            	
            	
            	
        



			
						
		
			
		
	}

