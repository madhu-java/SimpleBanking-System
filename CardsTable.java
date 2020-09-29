package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardsTable extends LuhnAlgorithem {
    String dbName;
    String tableName;
    SQLiteDataSource dataSource = new SQLiteDataSource();

    //CachedRowSetImpl crs = null;
    static int i = 0;

    public CardsTable(String[] args, String tableName) {
        this.tableName = tableName;
        //reading database name from commandline
        if (args[0].equals("-fileName")) {
            this.dbName = args[1];
        }
        //set url for sqlite database url
        String url = "jdbc:sqlite:C:\\sqlite\\bank.db";
        //create  SQLiteDataSource object
        //SQLiteDataSource dataSource = new SQLiteDataSource();
        //set url
        dataSource.setUrl(url);
        //establish connection

    }

    public void createCardTable() {

        //execute


        try (Connection con = dataSource.getConnection()) {
            Statement statement = con.createStatement();
            statement.executeUpdate("drop table card");
            statement.executeUpdate("create table if not exists " + tableName +
                    " (id INTEGER ," +
                    "number TEXT," +
                    "pin TEXT," +
                    "balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void insertCardInfo(Card card) {


        try (Connection con = dataSource.getConnection()) {
            Statement statement = con.createStatement();
            //statement.executeUpdate("delete from card");
            String sql = String.format("insert into card values(%d,'%s','%s',%d)", ++i, card.getCardNumber(),
                    String.valueOf(card.getPin()), card.getBalance());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public boolean checkIfCardExist(String cardNum, String pin) {

        boolean cardExist = true;
        //Scanner scanner = new Scanner(System.in);
        try (Connection con = dataSource.getConnection()) {
            Statement statement = con.createStatement();
            String sq = String.format("select * from card where number ='%s' and pin='%s'", cardNum, pin);
            ResultSet resultSet = statement.executeQuery(sq);
//                while(resultSet.next()){
//                    System.out.println("id "+resultSet.getInt("id"));
//                    System.out.println("number "+resultSet.getString("number"));
//                    System.out.println("pin "+resultSet.getString("pin"));
//                    System.out.println("bala "+resultSet.getInt("balance"));
//                }

            // if(resultSet.next()==false){
            //
            cardExist=resultSet.next();
//
//                        boolean exit = false;
//                        while(!exit) {
//                            System.out.println(s);
//                            int checkOption = scanner.nextInt();
//                            scanner.nextLine();
//                            switch (checkOption) {
//                                case 1:
//
//                                    System.out.println("Balance: " + bal);
//                                    break;
//                                case 2:
//                                    System.out.println("Enter income:");
//                                    int addIncome = scanner.nextInt();
//                                    int newBalance = addIncome + bal;
//                                    String updatesq = String.format("update %s set balance = %d where number =  %s",
//                                            tableName,newBalance,cardNum);
//                                    statement.executeUpdate(updatesq);
//                                    System.out.println("Income was added!");
//                                    break;
//                                case 3:
//                                    System.out.println("Transfer\n" +
//                                            "Enter card number:");
//                                    int tranferCardNum = Integer.parseInt(scanner.nextLine());
//                                    //If the user tries to transfer money to the same account,
//                                    // output the following message:
//                                    // “You can't transfer money to the same account!”
//                                    if (cardNum.equals(String.valueOf(tranferCardNum))) {
//                                        System.out.println("You can't transfer money to the same account!");
//                                        break;
//                                    } else {//If the receiver's card number doesn’t pass the Luhn algorithm
//                                        int lastDigit = validateWithLuhnAlgo(Arrays.asList(tranferCardNum));
//                                        if ((tranferCardNum % 10) != lastDigit) {
//                                            System.out.println("Probably you made mistake in the card number.+" +
//                                                    "Please try again!");
//                                            break;
//                                        }
//                                    }
//                                    try (ResultSet getTranferCardInfo = statement.executeQuery("Select * from " + tableName +
//                                            " where number = " + String.valueOf(tranferCardNum))) {
//                                        if (getTranferCardInfo == null) {
//                                            System.out.println("Enter how much money you want to transfer:");
//                                        } else {
//                                            System.out.println("Enter how much money you want to transfer:");
//                                            int amountToTranfer = scanner.nextInt();
//                                            if (amountToTranfer > bal) {
//                                                System.out.println("Not enough money!");
//                                            } else {
//                                                statement.executeUpdate("update " + tableName + " set balance = " +
//                                                        getTranferCardInfo.getInt("balance") + amountToTranfer +
//                                                        " where number= " + String.valueOf(tranferCardNum) + " ,balance = " + (bal - amountToTranfer) +
//                                                        " where number = " + cardNum);
//                                                System.out.println("Success!");
//                                            }
//                                        }
//                                    } catch (SQLException e) {
//
//                                        System.out.println(e.getErrorCode());
//                                        e.printStackTrace();
//
//
//                                    }
//
//                                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb");
//                                    break;
//                                case 4:
//                                    statement.executeUpdate("delete from "+tableName+" where number ="+cardNum);
//                                    break;
//                                case 5:
//                                    exit = true;
//                                    break;
//                                case 0:
//                                    exit =true;
//                                    exitFromMainmenu=true;
//                                    break;
//
//
//                            }
//                        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
return cardExist;
    }
    //  return exitFromMainmenu;
    public int getBalance(String cardNumber){
        int bal=0;
        try (Connection con = dataSource.getConnection()) {
            Statement statement = con.createStatement();
            //statement.executeUpdate("delete from card");
            String sql = String.format("select balance from card where number='%s'", cardNumber);
                   ResultSet row = statement.executeQuery(sql);
            bal=row.getInt("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bal;
    }
    public void updateBalance(String cardNumber,int newBalance){
        try (Connection con = dataSource.getConnection()) {
            Statement statement = con.createStatement();
            //statement.executeUpdate("delete from card");
            String updatesq = String.format("update %s set balance = %d where number =  %s",
                    tableName,newBalance,cardNumber);
            statement.executeUpdate(updatesq);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int checkValidate(String cardNumber){
        List<Integer> cardN = new ArrayList<>();
        for(int i=0;i<cardNumber.length();i++){
            int num=(int)cardNumber.charAt(i);
            cardN.add(num);

        }
       return  validateWithLuhnAlgo(cardN);
    }


    public boolean checkIfCardNumberExist(String cardNum) {

        boolean cardExist = false;

        try (Connection con = dataSource.getConnection()) {
            Statement statement = con.createStatement();
            String sq = "select number from card";
            ResultSet resultSet = statement.executeQuery(sq);
            while(resultSet.next()){
                if(cardNum.equals(resultSet.getString("number"))){
                    cardExist=true;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cardExist;
    }
    public void deleteAccount(String cardNumber){
        try (Connection con = dataSource.getConnection()) {
            Statement statement = con.createStatement();
            statement.executeUpdate("delete from "+tableName+" where number ="+cardNumber);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}



