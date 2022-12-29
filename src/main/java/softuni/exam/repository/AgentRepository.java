package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Agent;

// TODO:
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Agent findByFirstName(String firstName);

}
