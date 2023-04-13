package org.toyota.services;

import org.springframework.stereotype.Repository;
import org.toyota.dto.VehicleDTO;
import org.toyota.operationResult.DatabaseOpResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleManagement
{
    Optional<VehicleDTO> findById(Long id);

    Boolean existsById(Long id);

    Boolean existsByModel(String model);

    DatabaseOpResult save(VehicleDTO vehicleDTO);

    DatabaseOpResult update(VehicleDTO vehicleDTO);

    List<VehicleDTO> listVehicles();
}
