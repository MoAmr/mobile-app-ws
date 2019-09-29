package com.appsdevblog.app.ws.controller;

import com.appsdevblog.app.ws.domain_request.UserDetails;
import com.appsdevblog.app.ws.domain_response.*;
import com.appsdevblog.app.ws.exception.UserServiceException;
import com.appsdevblog.app.ws.service.AddressService;
import com.appsdevblog.app.ws.service.UserService;
import com.appsdevblog.app.ws.shared_dto.AddressDTO;
import com.appsdevblog.app.ws.shared_dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {

        UserRest returnedValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnedValue);

        return returnedValue;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest createUser(@RequestBody UserDetails userDetails) throws Exception {

        UserRest returnedValue = new UserRest();

        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        //UserDto userDto = new UserDto();
        //BeanUtils.copyProperties(userDetails, userDto);

        /** Another better way to map objects from source object to destination object */
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        returnedValue = modelMapper.map(createdUser, UserRest.class);

        return returnedValue;
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetails userDetails) {

        UserRest returnedValue = new UserRest();

        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser, returnedValue);

        return returnedValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {

        OperationStatusModel returnedValue = new OperationStatusModel();
        returnedValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnedValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnedValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        List<UserRest> returnedValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for (UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnedValue.add(userModel);
        }

        return returnedValue;
    }

    //localhost:8080/mobile-app-ws/users/89Pp4k8ceIah168rlbex7C6k9yfqw2/addresses

    /**
     * Applying HAL Format using Resources<> class of org.springframework.hateoas
     */

    @GetMapping(path = "/{id}/addresses",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
                    "application/hal+json"})
    public Resources<AddressesRest> getUserAddresses(@PathVariable String id) {

        List<AddressesRest> addressesListRestModel = new ArrayList<>();

        List<AddressDTO> addressDTO = addressService.getAddresses(id);

        if (addressDTO != null && !addressDTO.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {
            }.getType();
            addressesListRestModel = new ModelMapper().map(addressDTO, listType);

            for (AddressesRest addressesRest : addressesListRestModel) {

                Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id,
                        addressesRest.getAddressId())).withSelfRel();
                addressesRest.add(addressLink);

                Link userLink = linkTo(methodOn(UserController.class).getUser(id)).withRel("user");
                addressesRest.add(userLink);
            }
        }

        return new Resources<>(addressesListRestModel);
    }

    /** Applying HAL Format using Resource<> class of org.springframework.hateoas */

    @GetMapping(path = "/{userId}/addresses/{addressId}", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE,
            "application/hal+json"})
    public Resource<AddressesRest> getUserAddress(@PathVariable String userId, @PathVariable String addressId) {

        AddressDTO addressDTO = addressService.getAddress(addressId);

        ModelMapper modelMapper = new ModelMapper();

        /** Adding HATEOAS links for single address - list of addresses - user to include
         * links in returned response JSON object */

        /** methodOn method here inspects the UserController and then inspects the
         * getUserAddresses mapping adn helps linkTo method to build its link and
         * the correct path url that getUserAddress method requires without the
         * need to hardcode the "addresses" to the path url */

        /** withSelfRel() here associates a relation between the addressLink and itself
         * on the other hand, withRel() associates a relation between the hardcoded keyword
         * "user" ---> userLink, and "addresses" ---> addressesLink */

        Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId, addressId)).withSelfRel();
        Link userLink = linkTo(UserController.class).slash(userId).withRel("user");
        Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");

        AddressesRest addressesRestModel = modelMapper.map(addressDTO, AddressesRest.class);

        addressesRestModel.add(addressLink);
        addressesRestModel.add(userLink);
        addressesRestModel.add(addressesLink);

        return new Resource<>(addressesRestModel);
    }
}
