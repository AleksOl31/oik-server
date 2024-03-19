package ru.alexanna.oikserver.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
public class TechObject {
    protected int id;
    protected String name;
    protected Set<Location> locations = new HashSet<>();

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

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechObject that = (TechObject) o;

        if (id != that.id) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TechObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
