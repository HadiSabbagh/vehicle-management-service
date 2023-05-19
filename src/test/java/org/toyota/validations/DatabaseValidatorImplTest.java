package org.toyota.validations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.toyota.domain.vehicle.Vehicle;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class is responsible for checking constraint violations for the Vehicle entity when accessing the database.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class DatabaseValidatorImplTest
{
    @Autowired
    DatabaseValidatorImpl databaseValidator;

    List<String> violationsMsgs;
    List<String> expectedMsgs;
    Vehicle vehicle;

    @BeforeEach
    void setUp()
    {
        violationsMsgs = new ArrayList<>();
        vehicle = new Vehicle();
        expectedMsgs = new ArrayList<>();
    }

    @AfterEach
    void tearDown()
    {
        violationsMsgs = null;
        expectedMsgs = null;
    }


    /**
     * @param vehicle Test each vehicle
     * @param message Error Message that is expected from the validateVehicle method in DatabaseValidator class.
     */
    @ParameterizedTest
    @MethodSource("valuesGenerator")
    void existsByModel(Vehicle vehicle, String message)
    {
        violationsMsgs = databaseValidator.validateVehicle(vehicle);
        expectedMsgs.add(message);
        assertThat(violationsMsgs).isEqualTo(expectedMsgs);
    }

    /**
     * An argument generator to test multiple parameters.
     * @return The Vehicle and the expected Error Message
     */
    private static Stream<Arguments> valuesGenerator()
    {
        Vehicle existingVehicle = new Vehicle();
        existingVehicle.setMake("Toyota");
        existingVehicle.setModel("BAC2020");

        return Stream.of(
                Arguments.of(existingVehicle, "Error: model already exists")

        );
    }
}