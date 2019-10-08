package com.appsdevblog.app.ws.repository;

import com.appsdevblog.app.ws.io_entity.AddressEntity;
import com.appsdevblog.app.ws.io_entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {

        // Prepare User Entity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Mohamed");
        userEntity.setLastName("Amr");
        userEntity.setUserId("12a12a");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmailVerificationStatus(true);

        // Prepare Address Entity
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("qwer1234");
        addressEntity.setCity("Cairo");
        addressEntity.setCountry("Egypt");
        addressEntity.setPostalCode("ABC12");
        addressEntity.setStreetName("ABC St.");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        // Prepare User Entity
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Ali");
        userEntity2.setLastName("Amr");
        userEntity2.setUserId("12a12a12");
        userEntity2.setEmail("test1@test.com");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmailVerificationStatus(true);

        // Prepare Address Entity
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("qwer12345");
        addressEntity2.setCity("Cairo");
        addressEntity2.setCountry("Egypt");
        addressEntity2.setPostalCode("ABC12");
        addressEntity2.setStreetName("ABC St.");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);

        userEntity.setAddresses(addresses2);

        userRepository.save(userEntity2);
    }

    @Test
    final void testGetVerifiedUsers() {

        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
        // assertTrue(userEntities.size() == 2);
    }

}