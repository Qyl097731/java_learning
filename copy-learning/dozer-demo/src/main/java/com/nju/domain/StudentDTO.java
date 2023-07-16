package com.nju.domain;

/**
 * @description
 * @date 2023/7/14 0:47
 * @author: qyl
 */
public class StudentDTO {
    private String nam;

    private AddressDTO address;

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
