package com.onlineorder.oms.common.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class ApiError {
    private Instant timestamp;
    private String message;
    private String path;
    private ErrorCode code;
}
