package interview;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;

/**
 * Created by yannickgrenzinger on 10/12/2016.
 */
public class Banking {
    public static void main(String... args) {
        //LocalDate today = LocalDate.now();
        LocalDate firstYearOfNextYear = Year.of(2015).atDay(1);
        LocalDate endOfGame = firstYearOfNextYear.plus(Period.ofYears(6));
        Duration duration = Duration.between(firstYearOfNextYear.atStartOfDay(), endOfGame.atStartOfDay());
        final long daysOfExchange = duration.toDays();
        System.out.println("Nb of days : " + daysOfExchange);

        final long secondsOfGame = Duration.ofMinutes(120).getSeconds();
        final BigDecimal a = new BigDecimal(secondsOfGame);
        final BigDecimal b = new BigDecimal(daysOfExchange);
        final BigDecimal divide = a.divide(b, BigDecimal.ROUND_FLOOR);
        System.out.println("Value : " + divide.toEngineeringString());

    }


}
