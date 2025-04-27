package com.aboc.safetyNet.model.dto.response;

import java.util.List;

/**
 * DTO representing list of string.
 * Used for phoneAlert  method to SafetyNetService.
 * Get list of phoneNumber of person covered by specific firestation.
 */
public class PhoneAlertResponse {
    private List<String> phoneList;

    public PhoneAlertResponse() {
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }
}
