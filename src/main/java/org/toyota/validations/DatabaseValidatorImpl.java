package org.toyota.validations;

import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.toyota.dao.VehicleRepository;
import org.toyota.domain.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is responsible for checking constraint violations for the User entity when accessing the database.
 */
@Component
public class DatabaseValidatorImpl implements DatabaseValidator
{

    @Autowired
    Validator validator;

    @Autowired
    VehicleRepository vehicleRepository;


    private final Logger logger = LogManager.getLogger(DatabaseValidatorImpl.class);


    /**
     * @param vehicle Check whether the vehicle's model exists.
     * @return List of violations messages.
     */
    @Override
    public List<String> validateVehicle(Vehicle vehicle)
    {
        List<String> violationsMsg = new ArrayList<>();
        logger.info("Validating Vehicle");
        if (vehicleRepository.existsByModel(vehicle.getModel()))
        {
            violationsMsg.add("Error: model already exists");
        }

        return violationsMsg;
    }

}
