package org.toyota.services;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toyota.dao.VehicleRepository;
import org.toyota.domain.vehicle.Vehicle;
import org.toyota.dto.VehicleDTO;
import org.toyota.mapper.MessageResponse;
import org.toyota.mapper.VehicleDTOConverter;
import org.toyota.operationResult.DatabaseOpResult;
import org.toyota.validations.DatabaseValidatorImpl;
import org.toyota.validations.InputValidatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This service is responsible for crud operations and passing requests to the database through VehicleRepository.
 */
@Service
@Transactional
public class VehicleManagementImpl implements VehicleManagement
{
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    DatabaseValidatorImpl databaseValidator;

    @Autowired
    InputValidatorImpl inputValidator;

    @Autowired
    VehicleDTOConverter vehicleDTOConverter;

    private final Logger logger = LogManager.getLogger(VehicleManagementImpl.class);

    /**
     * @param id supply it to the repository to search for a user with this id
     * @return Either null or a VehicleDTO
     */
    @Override
    public Optional<VehicleDTO> findById(Long id)
    {

        //return Optional.ofNullable(vehicleDTOConverter.vehicle_to_DTO(vehicleRepository.findById(id).get()));
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return vehicle.map(value -> vehicleDTOConverter.vehicle_to_DTO(value));
    }

    @Override
    public Boolean existsById(Long id)
    {
        return vehicleRepository.existsById(id);
    }

    @Override
    public Boolean existsByModel(String model)
    {
        return vehicleRepository.existsByModel(model);
    }

    @Override
    public DatabaseOpResult save(VehicleDTO vehicleDTO)
    {
        Vehicle vehicle = vehicleDTOConverter.DTO_to_vehicle(vehicleDTO);
        List<String> databaseViolationsMsg = databaseValidator.validateVehicle(vehicle);
        List<String> inputViolationsMsg = inputValidator.validateVehicleInput(vehicleDTO);
        if (!databaseViolationsMsg.isEmpty())
        {
            logger.error("Violations found when saving vehicle." + databaseViolationsMsg.stream().toString());
            return new DatabaseOpResult(new MessageResponse("Violations found when saving vehicle: "), databaseViolationsMsg.stream().toList());
        }
        if (!inputViolationsMsg.isEmpty())
        {
            logger.error("Input violations found when saving vehicle." + inputViolationsMsg.stream().toString());
            return new DatabaseOpResult(new MessageResponse("Input violations found: "), inputViolationsMsg.stream().toList());
        } else
        {
            if (vehicle.getVehicle_defects() != null)
            {
                vehicle.getVehicle_defects().forEach(defect ->
                {
                    defect.setVehicle(vehicle);
                });
            }

            vehicleRepository.save(vehicle);
            vehicleRepository.flush();
            logger.info("Saved vehicle with model " + vehicle.getModel());
            return new DatabaseOpResult(new MessageResponse("Vehicle added"), null);
        }
    }


    @Override
    public DatabaseOpResult update(VehicleDTO vehicleDTO)
    {
        if(vehicleRepository.existsById(vehicleDTO.getVehicle_id()))
        {
            Vehicle vehicle = vehicleDTOConverter.DTO_to_vehicle(vehicleDTO);
            List<String> databaseViolationsMsg = databaseValidator.validateVehicle(vehicle);
            List<String> inputViolationsMsg = inputValidator.validateVehicleInput(vehicleDTO);
            if (!databaseViolationsMsg.isEmpty())
            {
                logger.error("Violations found when updating vehicle." + databaseViolationsMsg.stream().toString());
                return new DatabaseOpResult(new MessageResponse("Violations found when saving vehicle: "), databaseViolationsMsg.stream().toList());
            }
            if (!inputViolationsMsg.isEmpty())
            {
                logger.error("Input violations found when updating vehicle." + inputViolationsMsg.stream().toString());
                return new DatabaseOpResult(new MessageResponse("Input violations found: "), inputViolationsMsg.stream().toList());
            } else
            {
                vehicleRepository.save(vehicle);
                vehicleRepository.flush();
                logger.info("Updated vehicle with model " + vehicle.getModel());
                return new DatabaseOpResult(new MessageResponse("Vehicle updated"), null);
            }
        }
        else
        {
            List<String> doesNotExistMsg = new ArrayList<>();
            doesNotExistMsg.add("Vehicle does not exist");
            return new DatabaseOpResult(new MessageResponse("Vehicle updating failed"),doesNotExistMsg.stream().toList());
        }
    }

    @Override
    public List<VehicleDTO> listVehicles()
    {
        return vehicleDTOConverter.vehicles_To_DTOs(vehicleRepository.findAll());
    }
}
