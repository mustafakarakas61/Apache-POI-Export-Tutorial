package com.mustafa.apachepoitutorial.service;

import com.mustafa.apachepoitutorial.config.MapperConfig;
import com.mustafa.apachepoitutorial.dto.UserDTO;
import com.mustafa.apachepoitutorial.entity.UserEntity;
import com.mustafa.apachepoitutorial.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer createUser(UserDTO userDTO) {
        UserEntity userEntity = MapperConfig.getModelMapper().map(userDTO, UserEntity.class);

        return userRepository.save(userEntity).getId();
    }

    public Optional<UserEntity> readUser(Integer id) {

        return userRepository.findById(id);
    }

    public List<UserDTO> readUserList() {
        List<UserEntity> userlist = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (UserEntity user :
                userlist) {
            userDTOs.add(MapperConfig.getModelMapper().map(user, UserDTO.class));
        }

        return userDTOs;
    }

    public boolean updateUser(Integer id, UserDTO userDTO) {
        Optional<UserEntity> data = userRepository.findById(id);

        if(data.isPresent()) {
            if(Objects.nonNull(userDTO.getName())) {
                data.get().setName(userDTO.getName());
            }
            if(Objects.nonNull(userDTO.getSurname())) {
                data.get().setSurname(userDTO.getSurname());
            }
            if(Objects.nonNull(userDTO.getAge())) {
                data.get().setAge(userDTO.getAge());
            }
            if(Objects.nonNull(userDTO.getCity())) {
                data.get().setCity(userDTO.getCity());
            }
            if(Objects.nonNull(userDTO.getBirthday())) {
                data.get().setBirthday(userDTO.getBirthday());
            }
            userRepository.save(data.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(Integer id) {
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
