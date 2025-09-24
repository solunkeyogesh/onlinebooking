package com.onlineorder.oms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private AddressDto address;
}
