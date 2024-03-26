package ru.alexanna.oikserver.entities;

import java.util.*;


@SuppressWarnings("unused")
public class Port {
    protected int id;
    protected String name;
    protected int baudRate;
    protected boolean parity;
    protected String ktms;
    protected String receivedData;
    protected boolean online = false;
    protected List<CheckPoint> checkPoints = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public boolean isParity() {
        return parity;
    }

    public void setParity(boolean parity) {
        this.parity = parity;
    }

    public List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public String getKtms() {
        return ktms;
    }

    public void setKtms(String ktms) {
        this.ktms = ktms;
    }

    public String getReceivedData() {
        return receivedData;
    }

    public void setReceivedData(String receivedData) {
        this.receivedData = receivedData;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Port port = (Port) o;

        if (id != port.id) return false;
        if (baudRate != port.baudRate) return false;
        if (parity != port.parity) return false;
        return Objects.equals(name, port.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + baudRate;
        result = 31 * result + (parity ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baudRate=" + baudRate +
                ", parity=" + parity +
                ", ktms='" + ktms + '\'' +
                ", checkPoints=" + checkPoints +
                '}';
    }
}
