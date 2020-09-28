package banking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Card {

    //        String cardIIN = "400000";
    //initialisun card with card IIN 4000000
    ArrayList<Integer> card ;
    Random random;
    int pin;
    double balance;

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
        //card.add(random.nextInt(10));
        //validate card number with Luhn algorithm
        int checkSum = validateWithLuhnAlgo(card);
        card.add(checkSum);


    }

    private int validateWithLuhnAlgo(ArrayList<Integer> actualCard) {
        //drop the last digit
        //card.remove(card.size()-1);
        ArrayList<Integer> card =  new ArrayList<Integer>(actualCard);
        //multiply odd digits by 2;

        for(int i=0;i<card.size();i++){
            if(i%2==0) {
                int x = card.get(i) * 2;

                card.set(i, x);
            }
        }

        System.out.println("after multiply odd digits by 2");
        System.out.println(card);
        //subract 9 from numbers over 9
        for(int i=0;i<card.size();i++){

            int x = card.get(i);
            if(x>9){
                x -= 9;

                card.set(i,x);
            }
        }
        //get the sum of all digits of the card
        int sum = 0;
        for(int number : card){
            sum+= number;
        }
        //check if sum is divisible by 10
        int remainder = sum%10;
        int checkSum=0;
        if(remainder!=0){
            checkSum= 10-remainder;
        }
        //add checksum as last digit of card
        return checkSum;
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

    public Double getBalance() {
        return this.balance;
    }
}

