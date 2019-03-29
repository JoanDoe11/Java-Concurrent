package thread_pool.Factorizer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FactorizerImpl implements Factorizer {

    @Override
    public List<BigInteger> factorize(BigInteger number) {

        List<BigInteger> toReturn = new ArrayList<>();

        BigInteger divisor = new BigInteger("2");
        while(number.compareTo(BigInteger.ONE) == 1){
            if(number.mod(divisor) == BigInteger.ZERO){
                toReturn.add(divisor);
                number = number.divide(divisor);
            } else{
                divisor = divisor.add(BigInteger.ONE);
            }

        }

        return toReturn;
    }
}
