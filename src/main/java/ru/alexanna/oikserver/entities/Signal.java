package ru.alexanna.oikserver.entities;

import java.io.Serializable;
import java.util.Objects;


@SuppressWarnings("unused")
public class Signal implements Serializable {
    protected int id;
    protected String name;
    protected Integer number;
    protected Integer channel;
    protected CheckPoint checkPoint;

    public Signal() {
    }

    public Signal(String name, Integer number, CheckPoint checkPoint, Integer channel) {
        this.name = name;
        this.number = number;
        this.checkPoint = checkPoint;
        this.channel = channel;
    }

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

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer signalNumber) {
        this.number = signalNumber;
    }

    public CheckPoint getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(CheckPoint checkPoint) {
        this.checkPoint = checkPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Signal signal = (Signal) o;

        if (id != signal.id) return false;
        if (!Objects.equals(name, signal.name)) return false;
        return Objects.equals(number, signal.number);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", checkPoint=" + checkPoint.getId() +
                ", channel=" + channel +
                '}';
    }
}
