package softuni.exam.models.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @NotNull
    private BigDecimal price;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate publishedOn;

    @ManyToOne
    @NotNull
    private Apartment apartment;

    @ManyToOne
    @NotNull
    private Agent agent;

    public Offer() {
    }

    public Offer(BigDecimal price, LocalDate publishedOn, Apartment apartment, Agent agent) {
        this.price = price;
        this.publishedOn = publishedOn;
        this.apartment=apartment;
        this.agent=agent;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
