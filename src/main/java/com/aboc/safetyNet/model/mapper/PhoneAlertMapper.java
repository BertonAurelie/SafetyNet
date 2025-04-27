package com.aboc.safetyNet.model.mapper;

import com.aboc.safetyNet.model.dto.response.PhoneAlertResponse;

import java.util.List;

/**
 *  Converts a list of phone numbers into a PhoneAlertResponse DTO.
 *  Used for phoneAlert method of SafetyNetService.
 */
public class PhoneAlertMapper {

    public PhoneAlertMapper() {
    }

    public static PhoneAlertResponse toDto(List<String> phones) {
        PhoneAlertResponse phoneAlertListResponse = null;

        if (phones != null) {
            phoneAlertListResponse = new PhoneAlertResponse();

            phoneAlertListResponse.setPhoneList(phones);
        }
        return phoneAlertListResponse;
    }
}
