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
import org.toyota.dto.VehicleDTO;
import org.toyota.mapper.VehicleDTOConverter;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * This test checks whether  @NotNull and @NotEmpty as well as character limit annotations in Vehicle entity are working properly.
 * To understand how the expected message strings came to be, check Vehicle entity annotations and take a look at validateVehicleInput method.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class InputValidatorImplTest
{
    @Autowired
    InputValidatorImpl inputValidator;

    @Autowired
    VehicleDTOConverter vehicleDTOConverter;

    List<String> violationsMsgs;

    @BeforeEach
    void setUp()
    {
        violationsMsgs = new ArrayList<>();
    }

    @AfterEach
    void tearDown()
    {
        violationsMsgs = null;
    }

    @ParameterizedTest
    @MethodSource("valuesGenerator")
    void validateVehicleInput(VehicleDTO vehicleDTO, List<String> expectedMsgs)
    {
        violationsMsgs = inputValidator.validateVehicleInput(vehicleDTO);
        assertThat(violationsMsgs).hasSameElementsAs(expectedMsgs);
    }

    /**
     * An argument generator to test multiple parameters.
     * @return The Vehicle and the expected Error Message
     */
    private static Stream<Arguments> valuesGenerator()
    {
        List<String> expectedMsgs = new ArrayList<>();
        VehicleDTO emptyVehicleDTO = new VehicleDTO();
        // These messages are expected when a new VehicleDTO is created with no entries.
        expectedMsgs.add("model: Cannot be empty.");
        expectedMsgs.add("model: Cannot be null.");
        expectedMsgs.add("make: Cannot be empty.");
        expectedMsgs.add("make: Cannot be null.");

        //Valid VehicleDTO that should not contain any violation messages.
        VehicleDTO validVehicleDTO = new VehicleDTO();
        validVehicleDTO.setModel("BAK2020");
        validVehicleDTO.setMake("Toyota");

        //Bad model character size.
        VehicleDTO badModelCharSize = new VehicleDTO();
        badModelCharSize.setMake("Toyota");
        badModelCharSize.setModel("Toyo"); // should be 7 char long.
        return Stream.of(
                Arguments.of(emptyVehicleDTO,expectedMsgs),
                Arguments.of(validVehicleDTO, Collections.emptyList()),
                Arguments.of(badModelCharSize,Collections.singletonList("model: size must be between 7 and 7"))
        );
    }
}