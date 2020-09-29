package banking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Card extends LuhnAlgorithem {

    //        String cardIIN = "400000";
    //initialisun card with card IIN 4000000
    ArrayList<Integer> card ;
    Random random;
    int pin;
    int balance;

    //create card following Luhn algorithem
    public Card(){
        //initialising card with IIN 400000
        this.card=new ArrayList<>(Arrays.asList(4, 0, 0, 0, 0, 0));
        this.random = new Random();
        generateCard(card,random);
        this.pin=1000 + random.nextInt(8999);
        this.balance=0;
    }





    public void  generateCard(ArrayList<Integer> card,Random random){
        //adding customer Account number of 9 digits to the card
        for(int i=1;i<=9;i++){
            card.add(random.nextInt(10));
        }
        //add check sum
        card.add(random.nextInt(10));
        //validate card number with Luhn algorithm
        int checkSum = validateWithLuhnAlgo(card);
        card.set(card.size()-1,checkSum);


    }


    public String getCardNumber() {
        StringBuilder s = new StringBuilder();
        for(int number : card){
            s.append(number);
        }
        return s.toString();
    }

    public int getPin() {
        return this.pin;
    }

    public int getBalance() {
        return this.balance;
    }
}

