package softuni.exam.models.dto;

import java.math.BigDecimal;

public class BestOfferExportDto {

    private final String agentFullName;
    private final Long offerId;
    private final double apartmentArea;
    private final String townName;
    private final BigDecimal price;

    public BestOfferExportDto(String agentFullName, Long offerId, double apartmentArea, String townName, BigDecimal price) {
        this.agentFullName = agentFullName;
        this.offerId = offerId;
        this.apartmentArea = apartmentArea;
        this.townName = townName;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Agent %s with offer №%d:\n  -Apartment area: %.2f\n  --Town: %s\n  ---Price: %.2f$\n", agentFullName, offerId, apartmentArea, townName, price);
    }
}
