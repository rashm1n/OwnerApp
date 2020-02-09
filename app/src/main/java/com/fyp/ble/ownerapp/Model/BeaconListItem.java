package com.fyp.ble.ownerapp.Model;

public class BeaconListItem {
    String location;
    String description;
    String MAC;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BeaconListItem(String location, String description, String MAC) {
        this.location = location;
        this.description = description;
        this.MAC = MAC;
    }

    public BeaconListItem() {
    }

    public BeaconListItem(String location, String MAC) {
        this.location = location;
        this.MAC = MAC;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
