package strategy.interfaces;

import models.Ticket;

public interface PricingStrategy {
    double calculateFee(Ticket ticket);
}
