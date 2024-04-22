package com.ekstedt.individuelllab.services;

import com.ekstedt.individuelllab.entities.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberServiceInterface {
    List<Member> getAllMembers();

    Member getMemberById(Long id);

    ResponseEntity<Member> addMember(Member member);

    ResponseEntity<Member> updateMember(Long id, Member member);

    void deleteMemberById(Long id);

}