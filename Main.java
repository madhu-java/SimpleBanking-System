package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //reading database name from commandline
        String dbName="";
        if(args[0].equals("-fileName")) {
             dbName = args[1];
        }
        //set url for sqlite database url
        String url = "jdbc:sqlite:"+dbName;
        //create  SQLiteDataSource object
        SQLiteDataSource dataSource = new SQLiteDataSource();
        //set url
        dataSource.setUrl(url);
        //establish connection
        try (Connection con = dataSource.getConnection()) {
            //create statement
            Statement statement = con.createStatement();
           //execute
            statement.executeUpdate("create table if not exists card"+
                    "(id integer,"+
                    "number text,"+
                    "pin text,"+
                    "balance integer default 0)");

        }catch (SQLException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        String menu = "1. Create an account" +
                "\n2. Log into account" +
                "\n0. Exit";
        String loginMenu = "1. Balance\n2. Log out\n0. Exit";


        Map<String, Card> cards = new HashMap<>();
        boolean exit = false;
        while (!exit) {
            System.out.println(menu);
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    Card card = new Card();
                    //create card
                    // System.out.println("card created ");
                    //saving created card to map
                    cards.put(card.getCardNumber(), card);
                    //saving created card to card table
                    try(Connection con = dataSource.getConnection()){
                        try(Statement statement = con.createStatement()){
                            statement.executeUpdate("insert into card values("+
                                    1+","+
                                    card.getCardNumber()+","+
                                    card.getPin()+","+
                                    card.getBalance()+")");
                        }
                        catch(SQLException e){
                            e.printStackTrace();
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    // System.out.println("stored in cards");
                    System.out.printf("Your card has been created\nYour card number:\n%s\nYour card PIN:\n%d\n",
                            card.getCardNumber(), card.getPin());
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String enteredCNum = scanner.nextLine().trim();
                    System.out.println("Enter your PIN:");
                    int enteredPin = scanner.nextInt();
                    if (cards.size() > 0) {
                        if (cards.containsKey(enteredCNum)) {
                            Card cardObject = cards.get(enteredCNum);
                            if (enteredPin == cardObject.getPin()) {
                                System.out.println("You have successfully logged in!");
                                boolean exitFromLogin=false;
                                while (!exitFromLogin) {
                                    System.out.println(loginMenu);
                                    int loginoption = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (loginoption) {
                                        case 1:
                                            System.out.println("Balance: " + cardObject.getBalance());
                                            break;
                                        case 2:
                                            System.out.println("You have successfully logged out!");
                                            exitFromLogin=true;
                                            break;
                                        case 0:
                                            exitFromLogin=true;
                                            exit=true;
                                            break;

                                    }
                                }

                            } else {
                                System.out.println("Wrong card number or PIN!");
                                break;
                            }
                        } else {
                            System.out.println("Wrong card number or PIN!");
                            break;
                        }
                    }
                    break;
                case 0:
                    exit=true;
                    break;

                default:
                    System.out.println("Todo");
            }
        }

    }


}