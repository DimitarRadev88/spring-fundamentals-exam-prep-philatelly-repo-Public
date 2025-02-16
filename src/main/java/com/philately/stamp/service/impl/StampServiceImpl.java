package com.philately.stamp.service.impl;

import com.philately.stamp.dao.StampRepository;
import com.philately.stamp.dto.MyStampsServiceViewModel;
import com.philately.stamp.dto.OfferedStampsServiceViewModel;
import com.philately.stamp.dto.PurchasableStampServiceModel;
import com.philately.stamp.model.Stamp;
import com.philately.stamp.service.StampService;
import com.philately.user.dao.UserRepository;
import com.philately.user.model.User;
import com.philately.web.dto.StampAddBindingModel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StampServiceImpl implements StampService {

    private final StampRepository stampRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public StampServiceImpl(StampRepository stampRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.stampRepository = stampRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void doAdd(StampAddBindingModel stampAdd, UUID ownerId) {

        User owner = userRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("User not found!"));

        Stamp map = modelMapper.map(stampAdd, Stamp.class);
        map.setOwner(owner);

        stampRepository.save(map);

        log.info("{} added stamp with name {}!", owner.getUsername(), stampAdd.getName());
    }

    @Override
    public List<MyStampsServiceViewModel> getAllByUser(UUID userId) {
        return stampRepository.findAllByOwnerId(userId).stream().map(stamp -> {
            MyStampsServiceViewModel model = new MyStampsServiceViewModel();
            model.setName(stamp.getName());
            model.setDescription(stamp.getDescription());
            model.setImageUrl(stamp.getImageUrl());
            model.setPrice(stamp.getPrice());
            model.setUsedPaper(stamp.getPaper().getName().getFormattedName());
            return model;
        }).toList();
    }

    @Override
    public List<OfferedStampsServiceViewModel> getAllWithOwnerIdNotEqual(UUID userId) {
        return stampRepository
                .findAllByOwnerIdNotAndNotPurchased(userId)
                .stream()
                .map(s -> modelMapper.map(s, OfferedStampsServiceViewModel.class))
                .toList();
    }

    @Override
    public PurchasableStampServiceModel getWishedStamp(UUID stampId) {
        return modelMapper.map(stampRepository.findById(stampId)
                .orElseThrow(() -> new RuntimeException("Stamp not found!")), PurchasableStampServiceModel.class);
    }

    @Override
    public List<Stamp> getAllById(List<UUID> list) {
        return stampRepository.findAllById(list);
    }

}
