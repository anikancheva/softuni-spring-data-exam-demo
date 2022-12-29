package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    private static final String JSON_TOWNS_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    List<Town> towns;

    public TownServiceImpl(TownRepository townRepository, Gson gson, Validator validator) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validator = validator;
        this.towns = new ArrayList<>();
    }

    @Override
    public boolean areImported() {
        return townRepository.findAll().size()>0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Town[] townsArray = gson.fromJson(String.join("", Files.readAllLines(Path.of(JSON_TOWNS_PATH))), Town[].class);
        towns=List.of(townsArray);
        return gson.toJson(towns);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder result=new StringBuilder();
        if(towns.size()>0){
            towns.forEach(t->{
                if(validator.validate(t).isEmpty()){
                    townRepository.save(t);
                    result.append(String.format("Successfully imported town %s - %d", t.getTownName(), t.getPopulation())).append(System.lineSeparator());
                }else {
                    result.append("Invalid town").append(System.lineSeparator());
                }
            });


        }
        return result.toString();
    }
}
