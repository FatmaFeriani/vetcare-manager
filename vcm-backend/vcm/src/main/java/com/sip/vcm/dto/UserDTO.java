package com.sip.vcm.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String login;
    private String nom;
    private String prenom;
    private String tel;
    private List<String> roles;
}
