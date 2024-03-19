package ru.alexanna.oikserver.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@SuppressWarnings("unused")
public class CheckPoint {
    protected int id;
    protected String name;
    protected Integer address;
    protected Integer locationId;
    protected Integer portId;
    protected Set<Signal> signals = new HashSet<>();

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

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getLocation() {
        return locationId;
    }

    public void setLocation(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getPort() {
        return portId;
    }

    public void setPort(Integer portId) {
        this.portId = portId;
    }

    public Set<Signal> getSignals() {
        return signals;
    }

    public void setSignals(Set<Signal> signals) {
        this.signals = signals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckPoint that = (CheckPoint) o;

        if (id != that.id) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
//        String portId = (port == null) ? "null" : String.valueOf(port.getId());
//        String locationId = (location == null) ? "null" : String.valueOf(location.getId());
        return "CheckPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", locationID=" + locationId +
                ", portID=" + portId +
                '}';
    }
}
