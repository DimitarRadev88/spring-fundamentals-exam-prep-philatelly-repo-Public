package com.philately.stamp.service;

import com.philately.stamp.dto.MyStampsServiceViewModel;
import com.philately.stamp.dto.OfferedStampsServiceViewModel;
import com.philately.stamp.dto.PurchasableStampServiceModel;
import com.philately.stamp.model.Stamp;
import com.philately.web.dto.StampAddBindingModel;

import java.util.List;
import java.util.UUID;

public interface StampService {

    void doAdd(StampAddBindingModel stampAdd, UUID userId);

    List<MyStampsServiceViewModel> getAllByUser(UUID userId);

    List<OfferedStampsServiceViewModel> getAllWithOwnerIdNotEqual(UUID userId);

    PurchasableStampServiceModel getWishedStamp(UUID stampId);

    List<Stamp> getAllById(List<UUID> list);
}
