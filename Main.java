package banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String menu = "1. Create an account" +
                "\n2. Log into account" +
                "\n0. Exit";
        String loginMenu = "1. Balance\n" +

                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit\n";
//create table card in the provided database from commalind args
        CardsTable cardsTable = new CardsTable(args, "card");
        cardsTable.createCardTable();

        Map<String, Card> cards = new HashMap<>();
        boolean exit = false;
        while (!exit) {
            System.out.println(menu);
            int option = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            switch (option) {
                case 1:
                    Card card = new Card();
                    //create card
                    // System.out.println("card created ");
                    //saving created card to map
                    //cards.put(card.getCardNumber(), card);
                    //saving created card to card table
                    cardsTable.insertCardInfo(card);
                    // System.out.println("stored in cards");
                    System.out.printf("Your card have been created\nYour card number:\n%s\nYour card PIN:\n%d\n\n",
                            card.getCardNumber(), card.getPin());
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String enteredCNum = scanner.nextLine().trim();
                    System.out.println("Enter your PIN:");
                    String enteredPin = scanner.nextLine().trim();
                    System.out.println();
                    if (!cardsTable.checkIfCardExist(enteredCNum, enteredPin)) {
                        System.out.println("Wrong card number or PIN!");
                        break;
                    } else {
                        //if card exists
                        System.out.println("You have successfully logged in!");

                        boolean loginExit = false;
                        while (!loginExit) {
                            System.out.println(loginMenu);
                            int checkOption = scanner.nextInt();
                            scanner.nextLine();
                            switch (checkOption) {
                                case 1:

                                    System.out.println("Balance: " + cardsTable.getBalance(enteredCNum));
                                    break;
                                case 2:
                                    System.out.println("Enter income:");
                                    int addIncome = scanner.nextInt();
                                    int newBalance = addIncome + cardsTable.getBalance(enteredCNum);
                                    cardsTable.updateBalance(enteredCNum, newBalance);
                                    System.out.println("Income was added!");
                                    break;
                                case 3:
                                    System.out.println("Transfer\n" +
                                            "Enter card number:");
                                    String tranferCardNum = scanner.nextLine().trim();
                                    System.out.println("card read");
                                   // int tranferCardNum = Integer.parseInt(tranferCard);
                                    //If the user tries to transfer money to the same account,
                                    // output the following message:
                                    // “You can't transfer money to the same account!”
                                    if (enteredCNum.equals(String.valueOf(tranferCardNum))) {
                                        System.out.println("You can't transfer money to the same account!");
                                        break;
                                    } else if (tranferCardNum.charAt(tranferCardNum.length()-1) ==( cardsTable.checkValidate(tranferCardNum))) {
                                        //If the receiver's card number doesn’t pass the Luhn algorithm
                                        //int lastDigit = cardsTable.checkValidate(tranferCardNum);
                                        {
                                            System.out.println("Probably you made mistake in the card number.+" +
                                                    "Please try again!");
                                            break;
                                        }
                                    } else if (!cardsTable.checkIfCardNumberExist(tranferCardNum)) {
                                        System.out.println("Such a card does not exist.");
                                    } else {

                                        System.out.println("Enter how much money you want to transfer:");
                                        int amountToTranfer = scanner.nextInt();
                                        if (amountToTranfer > cardsTable.getBalance(enteredCNum)) {
                                            System.out.println("Not enough money!");
                                        } else {// cardsTable.tranferAmount(String.valueOf(tranferCardNum),enteredCNum,amountToTranfer);
                                            cardsTable.updateBalance(String.valueOf(tranferCardNum), amountToTranfer);
                                            cardsTable.updateBalance(enteredCNum, amountToTranfer * -1);
                                            System.out.println("Success!");
                                        }
                                    }

                                    break;
                                case 4:
                                    cardsTable.deleteAccount(enteredCNum);
                                    System.out.println("account deleted");
                                    break;
                                case 5:
                                    loginExit = true;
                                    break;
                                case 0:
                                    loginExit = true;
                                    exit = true;
                                    break;


                            }
                        }

                        }
                        break;
//                    if (cards.size() > 0) {
//                        if (cards.containsKey(enteredCNum)) {
//                            Card cardObject = cards.get(enteredCNum);
//                            if (enteredPin == cardObject.getPin()) {
//                                System.out.println("You have successfully logged in!");
//                                boolean exitFromLogin=false;
//                                while (!exitFromLogin) {
//                                    System.out.println(loginMenu);
//                                    int loginoption = scanner.nextInt();
//                                    scanner.nextLine();
//                                    switch (loginoption) {
//                                        case 1:
//                                            System.out.println("Balance: " + cardObject.getBalance());
//                                            break;
//                                        case 2:
//                                            System.out.println("You have successfully logged out!");
//                                            exitFromLogin=true;
//                                            break;
//                                        case 0:
//                                            exitFromLogin=true;
//                                            exit=true;
//                                            break;
//
//                                    }
//                                }
                        case 0:
                            exit = true;
                            break;

                        default:
                            System.out.println("Todo");
                    }
            }

        }


    }