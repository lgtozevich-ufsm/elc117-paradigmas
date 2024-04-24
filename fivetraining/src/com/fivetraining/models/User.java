package com.fivetraining.models;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private String cpf;
    private String name;
    private LocalDate birthDate;

    public User() {}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
