package com.modim.spring.domain.member.repository;

import com.modim.spring.domain.member.dto.MemberDto.MemberInfo;
import com.modim.spring.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByloginId(String loginId);
}
