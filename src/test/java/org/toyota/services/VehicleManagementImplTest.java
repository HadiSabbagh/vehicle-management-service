package org.toyota.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.toyota.domain.vehicle.Vehicle;
import org.toyota.dto.VehicleDTO;
import org.toyota.mapper.VehicleDTOConverter;
import org.toyota.validations.DatabaseValidatorImpl;
import org.toyota.validations.InputValidatorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class VehicleManagementImplTest
{

    @Autowired
    VehicleManagementImpl vehicleManagement;

    @Autowired
    VehicleDTOConverter vehicleDTOConverter;

    @Autowired
    DatabaseValidatorImpl databaseValidator;

    @Autowired
    InputValidatorImpl inputValidator;

    List<String> inputViolationMsgs;
    List<String> databaseViolationMsgs;
    List<String> expectedMsgs;

    @BeforeEach
    void setUp()
    {
        inputViolationMsgs = new ArrayList<>();
        databaseViolationMsgs = new ArrayList<>();
        expectedMsgs = new ArrayList<>();
    }

    @AfterEach
    void tearDown()
    {
        inputViolationMsgs = null;
        databaseViolationMsgs = null;
        expectedMsgs = null;
    }

    @ParameterizedTest
    @MethodSource("idGenerator")
    void nonExistingVehicleDTOById(Long id)
    {
        Optional<VehicleDTO> vehicleDTO = vehicleManagement.findById(id);
        assertThat(vehicleDTO).isEmpty();
    }

    @Test
    void doesNotExistById()
    {
        Long doesNotExist = 500L;
        assertThat(vehicleManagement.existsById(doesNotExist)).isFalse();
    }

    @Test
    void doesNotExistByModel()
    {
        String doesNotExist = "Model2022";
        assertThat(vehicleManagement.existsByModel(doesNotExist)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("vehicleDTOsGenerator")
    void inputAndDatabaseValidationsBeforeDatabaseOperation(VehicleDTO vehicleDTO, List<String> expectedCombinedMsgs)
    {
        Vehicle vehicle = vehicleDTOConverter.DTO_to_vehicle(vehicleDTO);
        databaseViolationMsgs = databaseValidator.validateVehicle(vehicle);
        inputViolationMsgs = (inputValidator.validateVehicleInput(vehicleDTO));

        List<String> actualCombinedMsgs = new ArrayList<>();
        actualCombinedMsgs.addAll(databaseViolationMsgs);
        actualCombinedMsgs.addAll(inputViolationMsgs);

        assertThat(expectedCombinedMsgs).hasSameElementsAs(actualCombinedMsgs);
    }


    @Test
    void updateNonExistingVehicle()
    {
        VehicleDTO doesNotExist = new VehicleDTO();
        doesNotExist.setVehicle_id(550L);
        List<String> doesNotExistMsg = new ArrayList<>();
        doesNotExistMsg.add("Vehicle does not exist");

        assertThat(vehicleManagement.update(doesNotExist).getErrorMessages()).hasSameElementsAs(doesNotExistMsg);
    }

    public static Stream<Arguments> idGenerator()
    {
        return Stream.of(
                Arguments.of(1000L),
                Arguments.of(5000L)
        );
    }

    public static Stream<Arguments> vehicleDTOsGenerator()
    {

        //Valid VehicleDTO
        VehicleDTO validDTO = new VehicleDTO();
        validDTO.setModel("CBA2021");
        validDTO.setMake("Toyota");


        //Invalid VehicleDTO (make must have a length of 7)
        VehicleDTO invalidDTO = new VehicleDTO();
        invalidDTO.setMake("bad");
        invalidDTO.setModel("Model");

        List<String> invalidExpectedCombinedMsgs = new ArrayList<>();
        invalidExpectedCombinedMsgs.add("model: size must be between 7 and 7");

        return Stream.of(
                Arguments.of(validDTO, Collections.emptyList()),
                Arguments.of(invalidDTO, invalidExpectedCombinedMsgs)
        );
    }
}