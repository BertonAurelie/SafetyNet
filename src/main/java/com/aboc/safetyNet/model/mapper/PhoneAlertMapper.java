package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.dto.response.PhoneAlertResponse;
import java.util.List;

public class PhoneAlertMapper {

    public PhoneAlertMapper(){}

    public static PhoneAlertResponse toDto(List<String> phones){
        PhoneAlertResponse phoneAlertListResponse = null;

        if(phones != null){
            phoneAlertListResponse = new PhoneAlertResponse();

            phoneAlertListResponse.setPhoneList(phones);
        }
        return phoneAlertListResponse;
    }
}
