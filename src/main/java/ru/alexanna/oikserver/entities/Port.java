package ru.alexanna.oikserver.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@SuppressWarnings("unused")
public class Port {
    private int id;
    private String name;
    private int baudRate;
    private boolean parity;
    private String ktms;
    protected Set<CheckPoint> checkPoints = new HashSet<>();

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

    public Set<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(Set<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public String getKtms() {
        return ktms;
    }

    public void setKtms(String ktms) {
        this.ktms = ktms;
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
