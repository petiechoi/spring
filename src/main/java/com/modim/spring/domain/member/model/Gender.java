package com.modim.spring.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    F("female"), M("male");
    private String gender;
}
