package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDtos.OfferImportDto;
import softuni.exam.models.dto.OfferImportDtos.OffersRootDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.XmlParser;

import javax.validation.Validator;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFERS_XML_PATH="src/main/resources/files/xml/offers.xml";
    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;

    private final ApartmentRepository apartmentRepository;

    private final XmlParser xmlParser;
    private final Validator validator;

    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository, XmlParser xmlParser, Validator validator) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.xmlParser = xmlParser;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.findAll().size()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_XML_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder result=new StringBuilder();

        List<OfferImportDto> offersDtos = xmlParser.fromXml(new File(OFFERS_XML_PATH), OffersRootDto.class).getOffers();
        offersDtos.forEach(o->{
            Agent agent=agentRepository.findByFirstName(o.getAgent().getName());
            if(null==agent){
                result.append("Invalid offer").append(System.lineSeparator());
            }else {
                Apartment apartment=apartmentRepository.getById(o.getApartment().getId());
                Offer offer=new Offer(o.getPrice(), o.getPublishedOn(), apartment, agent);
                if(validator.validate(offer).isEmpty()){
                    try {
                        offerRepository.save(offer);
                        result.append(String.format("Successfully imported offer %.2f", o.getPrice())).append(System.lineSeparator());
                    }catch (Exception e){
                        result.append("Invalid offer").append(System.lineSeparator());
                    }
                }else {
                    result.append("Invalid offer").append(System.lineSeparator());
                }
            }
        });
        return result.toString();
    }

    @Override
    public String exportOffers() {
        return null;
    }
}
