package com.modim.spring.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberCode {
    ID_DUPLICATE("중복된 ID입니다."),
    PW_NOTMATCH("비밀번호가 일치하지 않습니다.");
    private final String msg;
}
