package org.toyota.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.toyota.domain.vehicle.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>
{
    Boolean existsByModel(String model);
}
