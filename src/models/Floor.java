package models;

import enums.SlotType;
import enums.VehicleType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Floor {
    private int floorNumber;
    Map<SlotType, Queue<ParkingSlot>> freeSlots;

    Lock lock = new ReentrantLock();

    public Floor(int floorNumber, int small, int medium, int large) {
        this.floorNumber = floorNumber;
        freeSlots = new HashMap<>();

        freeSlots.put(SlotType.SMALL, new LinkedList<>());
        freeSlots.put(SlotType.MEDIUM, new LinkedList<>());
        freeSlots.put(SlotType.LARGE, new LinkedList<>());

        int id = floorNumber * 100;
        for(int i=0; i<small; i++) {
            freeSlots.get(SlotType.SMALL).add(new ParkingSlot(++id, SlotType.SMALL, floorNumber));
        }
        for(int i=0; i<medium; i++) {
            freeSlots.get(SlotType.MEDIUM).add(new ParkingSlot(++id, SlotType.MEDIUM, floorNumber));
        }
        for(int i=0; i<large; i++) {
            freeSlots.get(SlotType.LARGE).add(new ParkingSlot(++id, SlotType.LARGE, floorNumber));
        }
    }

    public ParkingSlot allocateSlot(Vehicle vehicle) {
        VehicleType vehicleType = vehicle.getVehicleType();
        lock.lock();
        try {
            if (vehicleType.equals(VehicleType.BIKE)) {
                return getSlot(SlotType.SMALL);
            } else if (vehicleType.equals(VehicleType.CAR)) {
                return getSlot(SlotType.MEDIUM) != null ? getSlot(SlotType.MEDIUM) : getSlot(SlotType.LARGE);
            } else {
                return getSlot(SlotType.LARGE);
            }
        } finally {
            lock.unlock();
        }
    }

    public void freeSlot(ParkingSlot parkingSlot) {
        lock.lock();
        try {
            freeSlots.get(parkingSlot.getSlotType()).offer(parkingSlot);
        } finally {
            lock.unlock();
        }
    }

    private ParkingSlot getSlot(SlotType slotType) {
        ParkingSlot parkingSlot = null;
        Queue q = freeSlots.get(slotType);
        if (q != null && !q.isEmpty()) {
            parkingSlot = (ParkingSlot) q.poll();
        }
        return parkingSlot;
    }
}
