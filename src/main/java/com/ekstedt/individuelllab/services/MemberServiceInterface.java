package com.ekstedt.individuelllab.services;

import com.ekstedt.individuelllab.entities.Member;

import java.util.List;

public interface MemberServiceInterface {
    List<Member> getAllMembers();

    Member getMemberById(Long id);

    void addMember(Member member);

    Member updateMember(Long id, Member member);

    void deleteMemberById(Long id);

}