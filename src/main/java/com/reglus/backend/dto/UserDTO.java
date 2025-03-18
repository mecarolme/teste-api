package com.reglus.backend.dto;

import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.enums.UserType;

import java.time.LocalDate;

public class UserDTO {
    private Long userId;
    private UserType userType;
    private String email;
    private String name;
    private LocalDate dateBirth;
    private String gender;
    private String disability;
    private String educationLevel;
    private String instituteName;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userType = user.getUserType();
        this.email = user.getEmail();
        this.name = user.getName();
        this.dateBirth = user.getDateBirth();
        this.gender = user.getGender();
        this.disability = user.getDisability();
        this.educationLevel = user.getEducationLevel();
        this.instituteName = user.getInstituteName();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
}