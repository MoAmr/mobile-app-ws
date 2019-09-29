package com.appsdevblog.app.ws.service;

import com.appsdevblog.app.ws.shared_dto.AddressDTO;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getAddresses(String userId);

    AddressDTO getAddress(String addressId);
}
