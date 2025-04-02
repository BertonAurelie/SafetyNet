package com.aboc.safetyNet.model.dto.response;

import java.util.List;

public class PhoneAlertResponse {
    private List<String> phoneList;

    public PhoneAlertResponse() {}

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }
}
