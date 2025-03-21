package it.tdgc.turnstile.util;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private Date timestamp;
    private Object metadata;

    public ApiResponse(String status, String message, T data, Date timestamp,  Object metadata) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
        this.metadata = metadata;
    }


}
