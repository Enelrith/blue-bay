package io.github.enelrith.bluebay.properties.repositories;

import io.github.enelrith.bluebay.properties.dto.CalculateNetPaymentDto;
import io.github.enelrith.bluebay.properties.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for the properties table
 */
public interface PropertyRepository extends JpaRepository<Property, Integer>, JpaSpecificationExecutor<Property> {
    boolean existsByAmaNumber(String amaNumber);

    Optional<Property> findByAmaNumber(String amaNumber);

    void deleteByAmaNumber(String amaNumber);

    boolean existsByIdAndIsActiveIs(Integer id, Boolean isActive);

    @Query("select new io.github.enelrith.bluebay.properties.dto.CalculateNetPaymentDto(p.nightlyRate, p.cleaningFee) " +
            "from Property p where p.id = :propertyId")
    CalculateNetPaymentDto findCalculateNetPaymentDtoById(@Param("propertyId") Integer propertyId);
}