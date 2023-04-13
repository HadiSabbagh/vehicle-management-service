package org.toyota.validations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.toyota.domain.vehicle.Vehicle;
import org.toyota.dto.VehicleDTO;
import org.toyota.mapper.VehicleDTOConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * This class is responsible for checking input violations before accessing the database.
 */
@Component
public class InputValidatorImpl implements InputValidator
{
    @Autowired
    Validator validator;


    @Autowired
    VehicleDTOConverter vehicleDTOConverter;

    private final Logger logger = LogManager.getLogger(InputValidatorImpl.class);


    @Override
    public List<String> validateVehicleInput(VehicleDTO vehicleDTO)
    {
        logger.info("Validating VehicleDTO");
        Vehicle vehicle = vehicleDTOConverter.DTO_to_vehicle(vehicleDTO);
        Set<ConstraintViolation<Vehicle>> violationSet = validator.validate(vehicle);
        List<String> violationsMsg = new ArrayList<>();
        violationSet.forEach(vehicleConstraintViolation ->
                                     violationsMsg.add(vehicleConstraintViolation.getPropertyPath() + ": " + vehicleConstraintViolation.getMessage()));
        return violationsMsg;
    }

}
