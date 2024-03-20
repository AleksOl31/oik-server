package ru.alexanna.oikserver.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
public class Location {
    protected int id;
    protected String name;
//    protected TechObject techObject;
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

//    public TechObject getTechObject() {
//        return techObject;
//    }

//    public void setTechObject(TechObject techObject) {
//        this.techObject = techObject;
//    }

    public Set<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(Set<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (id != location.id) return false;
        return Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", techObjectID=" + techObject.getId() +
//                ", checkPoints=" + checkPoints +
                '}';
    }
}
