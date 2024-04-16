package com.example.service;

import java.util.HashSet;
import java.util.Set;

public class ZipData {
    private Set<String> policyData;
    private Set<String> pipData;
    private String configData;
    private String zipFileName;

    public Set<String> getPolicyData() {
        return policyData;
    }

    public void setPolicyData(Set<String> policyData) {
        this.policyData = policyData;
    }

    public Set<String> getPipData() {
        return pipData;
    }

    public void setPipData(Set<String> pipData) {
        this.pipData = pipData;
    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }

    public String getConfigData() {
        return configData;
    }

    public void setConfigData(String configData) {
        this.configData = configData;
    }

    public void addPipData(String s) {
        if (pipData == null) {
            pipData = new HashSet<>();
        }
        pipData.add(s);
    }

    public void addPolicyData(String s) {
        if (policyData == null) {
            policyData = new HashSet<>();
        }
        policyData.add(s);
    }
}
