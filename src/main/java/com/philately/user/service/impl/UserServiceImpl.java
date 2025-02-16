package com.philately.user.service.impl;

import com.philately.stamp.dto.PurchasableStampServiceModel;
import com.philately.stamp.service.StampService;
import com.philately.user.dao.UserRepository;
import com.philately.user.dto.LoggedInUserServiceModel;
import com.philately.user.model.User;
import com.philately.user.service.UserService;
import com.philately.web.dto.UserLoginBindingModel;
import com.philately.web.dto.UserRegisterBindingModel;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final StampService stampService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, StampService stampService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.stampService = stampService;
    }

    @Override
    public String doRegister(UserRegisterBindingModel userRegister) {

        if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match!");
        }

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userRegister.getUsername(), userRegister.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String errorMessage = user.getUsername().equals(userRegister.getUsername())
                    ? "Username already exists!"
                    : "Email already exists!";
            throw new RuntimeException(errorMessage);
        }

        User save = userRepository.save(modelMapper.map(userRegister, User.class));

        log.info("Saved user: {}", save.getUsername());

        return save.getUsername();

    }

    @Override
    @Transactional
    public LoggedInUserServiceModel getLoggedInUser(UUID id) {
        User user = userRepository.findById(id).get();

        LoggedInUserServiceModel loggedIn = modelMapper.map(user, LoggedInUserServiceModel.class);
        loggedIn.setMyStamps(stampService.getAllByUser(user.getId()));
        loggedIn.setWishedStamps(new LinkedHashSet<>());

        return loggedIn;
    }

    @Override
    @Transactional
    public void purchaseStampsForUser(UUID id, Set<PurchasableStampServiceModel> wishedStamps) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.getPurchasedStamps()
                .addAll(stampService.getAllById(wishedStamps.stream()
                        .map(PurchasableStampServiceModel::getId).toList()));
    }

    @Override
    @Transactional
    public LoggedInUserServiceModel doLogin(UserLoginBindingModel userLogin) {
        Optional<User> optionalUser = userRepository.getUserByUsername(userLogin.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid username or password!");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        getLoggedInUser(user.getId());

        log.info("Logged in user: {}", user.getUsername());

        return getLoggedInUser(user.getId());

    }
}
