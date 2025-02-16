package com.philately.user.dto;

import com.philately.stamp.dto.MyStampsServiceViewModel;
import com.philately.stamp.dto.PurchasableStampServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInUserServiceModel {

    private UUID id;
    private String username;
    private List<MyStampsServiceViewModel> myStamps;
    private Set<PurchasableStampServiceModel> wishedStamps;
    private List<PurchasedStamps> purchasedStamps;

}
