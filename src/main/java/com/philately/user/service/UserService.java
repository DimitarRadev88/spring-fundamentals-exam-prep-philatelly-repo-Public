package com.philately.user.service;

import com.philately.stamp.dto.PurchasableStampServiceModel;
import com.philately.user.dto.LoggedInUserServiceModel;
import com.philately.user.model.User;
import com.philately.web.dto.UserLoginBindingModel;
import com.philately.web.dto.UserRegisterBindingModel;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    LoggedInUserServiceModel doLogin(UserLoginBindingModel userLogin);

    String doRegister(UserRegisterBindingModel userRegister);

    LoggedInUserServiceModel getLoggedInUser(UUID id);

    void purchaseStampsForUser(UUID id, Set<PurchasableStampServiceModel> wishedStamps);
}
