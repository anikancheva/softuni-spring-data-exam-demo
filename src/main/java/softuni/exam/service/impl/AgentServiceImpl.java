package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    private static final String JSON_AGENTS_PATH = "src/main/resources/files/json/agents.json";
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    List<AgentImportDto> agents;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, Gson gson, Validator validator) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validator = validator;
        this.agents = new ArrayList<>();
    }

    @Override
    public boolean areImported() {
        return agentRepository.findAll().size() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        //the agent has a town, but the town has only a name!
        agents = List.of(gson.fromJson(String.join("", Files.readAllLines(Path.of(JSON_AGENTS_PATH))), AgentImportDto[].class));
        return gson.toJson(agents);

    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder result = new StringBuilder();
        if (agents.size() > 0) {
            agents.forEach(a -> {
                Town town=townRepository.findByTownName(a.getTown());
                Agent agent=new Agent(a.getFirstName(), a.getLastName(), a.getEmail(), town);
                if (validator.validate(agent).isEmpty()) {
                    try {
                        agentRepository.save(agent);
                        result.append(String.format("Successfully imported agent - %s %s", a.getFirstName(), a.getLastName())).append(System.lineSeparator());
                    } catch (Exception e) {
                        result.append("Invalid agent").append(System.lineSeparator());
                    }


                } else {
                    result.append("Invalid agent").append(System.lineSeparator());
                }
            });


        }
        return result.toString();
    }
}
