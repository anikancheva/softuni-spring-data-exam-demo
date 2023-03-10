package softuni.exam.models.dto.ApartmentImportDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentsRootDto {

    @XmlElement(name = "apartment")
    private List<ApartmentDto> apartments;

    public List<ApartmentDto> getApartments() {
        return apartments;
    }
}
