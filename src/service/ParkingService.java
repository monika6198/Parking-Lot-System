package service;

import strategy.interfaces.PricingStrategy;
import models.Floor;
import models.ParkingSlot;
import models.Ticket;
import models.Vehicle;
import repository.ParkingRepository;

import java.util.concurrent.atomic.AtomicInteger;

public class ParkingService {
    private ParkingRepository repository;
    private PricingStrategy pricingStrategy;

    private static final AtomicInteger ticketCounter = new AtomicInteger(1);

    public ParkingService(ParkingRepository repository, PricingStrategy pricingStrategy) {
        this.repository = repository;
        this.pricingStrategy = pricingStrategy;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for(Floor floor : repository.getFloors()) {
            ParkingSlot parkingSlot = floor.allocateSlot(vehicle);
            if (parkingSlot != null) {
                Ticket ticket = new Ticket(ticketCounter.getAndIncrement(), vehicle, parkingSlot);
                repository.saveTicket(ticket);
                return ticket;
            }
        }
        System.out.println("No slots available");
        return null;
    }

    public double unparkVehicle(int ticketId) {
        Ticket ticket = repository.removeAndGetTicket(ticketId);
        if (ticket == null) {
            throw new RuntimeException("Invalid ticket");
        }
        ParkingSlot slot = ticket.getParkingSlot();
        Floor floor = repository.getFloor(slot.getFloorNumber());
        floor.freeSlot(slot);

        double fee = pricingStrategy.calculateFee(ticket);
        return fee;
    }
}
