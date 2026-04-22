import enums.VehicleType;
import models.Ticket;
import models.Vehicle;
import repository.ParkingRepository;
import service.ParkingService;
import strategy.interfaces.HourlyPricingStrategy;
import strategy.interfaces.PricingStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParkingLotApp {

    public static void main(String[] args) throws InterruptedException {

        ParkingRepository repo = new ParkingRepository();
        PricingStrategy pricing = new HourlyPricingStrategy();

        ParkingService service = new ParkingService(repo, pricing);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i=0; i<10; i++) {
            int userId = i;

            executor.submit(() -> {
                try {
                    Vehicle vehicle = new Vehicle("KA" + userId, VehicleType.CAR);

                    System.out.println("Thread " + userId + " trying to park");

                    Ticket ticket = service.parkVehicle(vehicle);
                    if (ticket != null) {
                        System.out.println("Thread " + userId +
                                " parked at slot " + ticket.getParkingSlot().getId());

                        // simulate parking time
                        Thread.sleep(100);

                        double fee = service.unparkVehicle(ticket.getTicketId());

                        System.out.println("Thread " + userId +
                                " unparked. Fee: " + fee);
                    } else {
                        System.out.println("Thread " + userId + " could not find slot");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();

        // wait for all threads to finish
        while (!executor.isTerminated()) {
            Thread.sleep(1000);
        }
        System.out.println("All threads finished");
    }
}