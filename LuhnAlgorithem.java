package banking;

import java.util.ArrayList;
import java.util.List;

public class LuhnAlgorithem {
    public int validateWithLuhnAlgo(List<Integer> actualCard) {
        ArrayList<Integer> card =  new ArrayList<Integer>(actualCard);
        //drop the last digit
        card.remove(card.size()-1);

        //multiply odd digits by 2;

        for(int i=0;i<card.size();i++){
            if(i%2==0) {
                int x = card.get(i) * 2;

                card.set(i, x);
            }
        }

        //System.out.println("after multiply odd digits by 2");
        //System.out.println(card);
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
}
