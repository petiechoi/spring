package com.modim.spring.domain.borrow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status{
    POSSIBLE("대여가능"),
    PROGRESS("대여중"),
    APPLY("대여신청중");

    private final String status;
}
