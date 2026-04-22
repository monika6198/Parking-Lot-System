package strategy.interfaces;

import models.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyPricingStrategy implements PricingStrategy {
    private static final double RATE_PER_HOUR = 10;

    @Override
    public double calculateFee(Ticket ticket) {
        long hours = Duration.between(
                ticket.getEntryTime(),
                LocalDateTime.now()
        ).toHours();

        return Math.max(10, hours * RATE_PER_HOUR);
    }
}
