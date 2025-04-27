package com.aboc.safetyNet.model.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Response DTO representing the list of children living at a given address
 * used for the child alert endpoint.
 * take a list of childWithFamilyResponse(information of child(firstName, lastName, age) & a list of him family member).
 */
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
