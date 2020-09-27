package banking;

import java.util.Random;

public class Card {

        String cardIIN = "400000";
        Random random = new Random();

        int custAccNumber = 100000000+random.nextInt(899999999);
        int chechSum = random.nextInt(10);
        String custCard = cardIIN + custAccNumber + chechSum;
        int pin = 1000+random.nextInt(8999);
        double balance = 0;

        public String getCardNumber() {
            return this.custCard;
        }

        public int getPin() {
            return this.pin;
        }

        public Double getBalance() {
            return this.balance;
        }
    }


