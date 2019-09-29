package com.appsdevblog.app.ws.service_impl;

import com.appsdevblog.app.ws.io_entity.AddressEntity;
import com.appsdevblog.app.ws.io_entity.UserEntity;
import com.appsdevblog.app.ws.repository.AddressRepository;
import com.appsdevblog.app.ws.repository.UserRepository;
import com.appsdevblog.app.ws.service.AddressService;
import com.appsdevblog.app.ws.shared_dto.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {

        List<AddressDTO> returnedValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) return returnedValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity : addresses) {
            returnedValue.add(modelMapper.map(addressEntity, AddressDTO.class));
        }
        return returnedValue;
    }

    @Override
    public AddressDTO getAddress(String addressId) {

        AddressDTO returnedValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if (addressEntity != null) {
            returnedValue = new ModelMapper().map(addressEntity, AddressDTO.class);
        }

        return returnedValue;
    }
}
