package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDtos.ApartmentDto;
import softuni.exam.models.dto.ApartmentImportDtos.ApartmentsRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.RoomsEnum;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.XmlParser;

import javax.validation.Validator;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final String APARTMENTS_XML_PATH = "src/main/resources/files/xml/apartments.xml";
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, XmlParser xmlParser, Validator validator, ModelMapper modelMapper) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.findAll().size() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_XML_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        ApartmentsRootDto apartmentsXml = xmlParser.fromXml(new File(APARTMENTS_XML_PATH), ApartmentsRootDto.class);
        List<ApartmentDto> aptDtos = apartmentsXml.getApartments();
        List<Apartment> apartments = aptDtos.stream().map(ap -> {
            Town town = townRepository.findByTownName(ap.getTown());
            return new Apartment(RoomsEnum.valueOf(ap.getApartmentType()), ap.getArea(), town);
        }).toList();
        StringBuilder result=new StringBuilder();
        apartments.forEach(a->{
            if(validator.validate(a).isEmpty()){
                try {
                    apartmentRepository.save(a);
                    result.append(String.format("Successfully imported apartment %s - %.2f", a.getApartmentType().toString(), a.getArea())).append(System.lineSeparator());
                }catch (Exception e){
                    result.append("Invalid apartment").append(System.lineSeparator());
                }

            }else {
                result.append("Invalid apartment").append(System.lineSeparator());
            }
        });
        return result.toString();
    }
}
