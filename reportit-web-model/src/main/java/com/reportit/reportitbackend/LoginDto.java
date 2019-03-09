package com.reportit.reportitbackend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto extends ResponseModel implements Serializable{

    private static final long serialVersionUID = 112333L;

    private String username;
    private String password;
    private String email;
    private String phoneNo;
    private String location;
    private String userId;
    private String gcmToken;
}
