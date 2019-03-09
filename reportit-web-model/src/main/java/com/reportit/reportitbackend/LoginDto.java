package com.reportit.reportitbackend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable{

    private static final long serialVersionUID = 112333L;

    private String username;
    private String password;
    private String email;
    private String phoneNo;
    @NonNull
    private String location;
}
