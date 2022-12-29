package softuni.exam.models.dto.OfferImportDtos;


import softuni.exam.util.XmlLocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {

    @XmlElement
    private BigDecimal price;
    @XmlElement
    private AgentRootDto agent;
    @XmlElement
    private ApartmentRootDto apartment;
    @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
    private LocalDate publishedOn;

    public BigDecimal getPrice() {
        return price;
    }

    public AgentRootDto getAgent() {
        return agent;
    }

    public ApartmentRootDto getApartment() {
        return apartment;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }
}
