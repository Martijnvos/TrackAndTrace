package classes;

import enums.ShippingType;
import enums.Status;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Package implements Serializable {
    private int ID;
    private String name;
    private String fromCompany;
    private ShippingType shippingType;
    private Status status;
    private double size;
    private int weight;
    private String contents;
    private LocalDate expectedDeliveryDate;
    private double locationLat;
    private double locationLong;

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFromCompany() {
        return fromCompany;
    }
    public void setFromCompany(String fromCompany) {
        this.fromCompany = fromCompany;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }
    public void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public double getLocationLat() {
        return locationLat;
    }
    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLong() {
        return locationLong;
    }
    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    public Package(int ID, String name, String fromCompany, ShippingType shippingType, Status status,
                   double size, int weight, String contents, LocalDate expectedDeliveryDate,
                   double locationLat, double locationLong) {
        this.ID = ID;
        this.name = name;
        this.fromCompany = fromCompany;
        this.shippingType = shippingType;
        this.status = status;
        this.size = size;
        this.weight = weight;
        this.contents = contents;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }

    public Package(String name, String fromCompany, ShippingType shippingType, Status status,
                   double size, int weight, String contents, LocalDate expectedDeliveryDate,
                   double locationLat, double locationLong) {
        this.name = name;
        this.fromCompany = fromCompany;
        this.shippingType = shippingType;
        this.status = status;
        this.size = size;
        this.weight = weight;
        this.contents = contents;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }
}
