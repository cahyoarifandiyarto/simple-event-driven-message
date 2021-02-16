package com.service.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.user.dto.UserDTO;
import com.service.user.entity.User;
import com.service.user.repository.UserRepository;
import com.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;

    @Override
    public Long createUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresent(user -> {
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    raiseEvent(userDTO);
                });
    }

    private void raiseEvent(UserDTO userDTO) {
        try {
            String value = OBJECT_MAPPER.writeValueAsString(userDTO);
            kafkaTemplate.sendDefault(userDTO.getId(), value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
