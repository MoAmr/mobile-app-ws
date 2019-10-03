package com.appsdevblog.app.ws.controller;

import com.appsdevblog.app.ws.domain_response.UserRest;
import com.appsdevblog.app.ws.service.UserService;
import com.appsdevblog.app.ws.shared_dto.AddressDTO;
import com.appsdevblog.app.ws.shared_dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    UserDto userDto;

    final String USER_ID = "qwer1234";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Mohammed");
        userDto.setLastName("Amr");
        userDto.setEncryptedPassword("asdf1234");
        userDto.setEmail("test@test.com");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setUserId(USER_ID);
    }

    @Test
    final void TestGetUser() {

        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getLastName(), userRest.getLastName());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
    }

    private List<AddressDTO> getAddressesDto() {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setType("shipping");
        addressDTO.setCity("Cairo");
        addressDTO.setCountry("Egypt");
        addressDTO.setPostalCode("ABC123");
        addressDTO.setStreetName("123 Street");

        AddressDTO billingAddressDTO = new AddressDTO();
        addressDTO.setType("billing");
        addressDTO.setCity("Cairo");
        addressDTO.setCountry("Egypt");
        addressDTO.setPostalCode("ABC123");
        addressDTO.setStreetName("123 Street");

        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDTO);
        addresses.add(billingAddressDTO);

        return addresses;

    }
}