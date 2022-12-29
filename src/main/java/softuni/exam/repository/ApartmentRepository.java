package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.RoomsEnum;

import javax.validation.constraints.NotNull;
import java.util.List;

// TODO:
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findAllByApartmentTypeOrderByAreaDesc(@NotNull RoomsEnum apartmentType);
}
