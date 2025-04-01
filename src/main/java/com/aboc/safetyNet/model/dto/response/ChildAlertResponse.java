package com.aboc.safetyNet.model.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ChildAlertResponse {
    List<ChildWithFamilyResponse> children = new ArrayList<>();

    public ChildAlertResponse(List<ChildWithFamilyResponse> children) {
        this.children = children;
    }

    public List<ChildWithFamilyResponse> getChildren() {
        return children;
    }

    public void setChildren(List<ChildWithFamilyResponse> children) {
        this.children = children;
    }

}
