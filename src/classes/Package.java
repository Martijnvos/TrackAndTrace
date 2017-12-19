package classes;

import enums.ShippingType;
import enums.Status;

import java.util.Date;

public class Package {
    private int ID;
    private String name;
    private String fromCompany;
    private ShippingType shippingType;
    private Status status;
    private double size;
    private double weight;
    private String contens;
    private Date expectedDeliveryDate;
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

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getContens() {
        return contens;
    }
    public void setContens(String contens) {
        this.contens = contens;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }
    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
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
                   double size, double weight, String contens, Date expectedDeliveryDate,
                   double locationLat, double locationLong) {
        this.ID = ID;
        this.name = name;
        this.fromCompany = fromCompany;
        this.shippingType = shippingType;
        this.status = status;
        this.size = size;
        this.weight = weight;
        this.contens = contens;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }

    public Package(String name, String fromCompany, ShippingType shippingType, Status status,
                   double size, double weight, String contens, Date expectedDeliveryDate,
                   double locationLat, double locationLong) {
        this.name = name;
        this.fromCompany = fromCompany;
        this.shippingType = shippingType;
        this.status = status;
        this.size = size;
        this.weight = weight;
        this.contens = contens;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }
}
