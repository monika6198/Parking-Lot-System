package repository;

import models.Floor;
import models.Ticket;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingRepository {
    private Map<Integer, Floor> floors = new ConcurrentHashMap<>();
    private Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();

    public ParkingRepository() {
        // 2 floors
        floors.put(1, new Floor(1, 2, 2, 1));
        floors.put(2, new Floor(2, 2, 2, 1));
    }

    public Collection<Floor> getFloors() {
        return floors.values();
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    public void saveTicket(Ticket ticket) {
        tickets.put(ticket.getTicketId(), ticket);
    }

    public Ticket getTicket(int id) {
        return tickets.get(id);
    }

    public Ticket removeAndGetTicket(int id) {
        return tickets.remove(id);
    }
}
