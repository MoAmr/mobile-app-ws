package com.appsdevblog.app.ws.repository;

import com.appsdevblog.app.ws.io_entity.AddressEntity;
import com.appsdevblog.app.ws.io_entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);

    AddressEntity findByAddressId(String addressId);
}
