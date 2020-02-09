package com.fyp.ble.ownerapp.Model;

public class IniBeacon {
String description;
String MAC;

    public IniBeacon(String description, String MAC) {
        this.description = description;
        this.MAC = MAC;
    }

    public IniBeacon() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
