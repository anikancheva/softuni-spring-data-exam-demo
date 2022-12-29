package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Offer;

import java.util.List;

// TODO:
public interface OfferRepository extends JpaRepository<Offer, Long> {


    List<Offer> findAllByApartmentIdOrderByPriceAsc(Long id);
}
