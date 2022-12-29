package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "apartments", uniqueConstraints = @UniqueConstraint(columnNames = {"area", "town_id"}))

public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private RoomsEnum apartmentType;

    @DecimalMin("40.00")
    @NotNull
    private double area;

    @ManyToOne
    @NotNull
    private Town town;

    public Apartment() {
    }

    public Apartment(RoomsEnum apartmentType, double area, Town town) {
        this.apartmentType = apartmentType;
        this.area = area;
        this.town=town;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomsEnum getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(RoomsEnum apartmentType) {
        this.apartmentType = apartmentType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

}
