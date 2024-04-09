package com.ekstedt.individuelllab.services;

import com.ekstedt.individuelllab.entities.Member;
import com.ekstedt.individuelllab.exceptions.ResourceNotFoundException;
import com.ekstedt.individuelllab.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServices implements MemberServiceInterface {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        if (memberRepository.existsById(id)) {
            Optional<Member> member = memberRepository.findById(id);
            if (member.isPresent()) {
                return member.get();
            }
        }
        throw new ResourceNotFoundException("Member", "id", id);
    }

    @Override
    public void addMember(Member member) {
        memberRepository.save(member);

    }

    @Override
    public Member updateMember(Long id, Member updatedMember) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member existingMember = optionalMember.get();
            existingMember.setFirstName(updatedMember.getFirstName());
            existingMember.setLastName(updatedMember.getLastName());
            existingMember.setAddress(updatedMember.getAddress());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setPhone(updatedMember.getPhone());
            existingMember.setDateOfBirth(updatedMember.getDateOfBirth());

            return memberRepository.save(existingMember);
        } else {
            throw new ResourceNotFoundException("Member", "id", id);
        }
    }

    @Override
    public void deleteMemberById(Long id) {
        memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
        memberRepository.deleteById(id);

    }
}