package models;

import enums.SlotType;

public class ParkingSlot {
    private int id;
    private SlotType slotType;
    private int floorNumber;

    public ParkingSlot(int id, SlotType slotType, int floorNumber) {
        this.id = id;
        this.slotType = slotType;
        this.floorNumber = floorNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}
