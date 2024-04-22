package com.ekstedt.individuelllab.services;

import com.ekstedt.individuelllab.entities.Address;
import com.ekstedt.individuelllab.entities.Member;
import com.ekstedt.individuelllab.exceptions.ResourceNotFoundException;
import com.ekstedt.individuelllab.repositories.AddressRepository;
import com.ekstedt.individuelllab.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServices implements MemberServiceInterface {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AddressRepository addressRepository;

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
    public ResponseEntity<Member> addMember(Member member) {
        // Kontrollera om medlemmen har en giltig adress
        if (member.getAddress() == null) {
            // Om medlemmen inte har en adress, returneras ett felstatus meddelande
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Hämta den befintliga adressen från databasen baserat på adressens id
        Long addressId = member.getAddress().getId();
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        // Tilldela den befintliga adressen till den nya medlemmen
        member.setAddress(existingAddress);

        Member savedMember = memberRepository.save(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }


    @Override
    public ResponseEntity<Member> updateMember(Long id, Member updatedMember) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member existingMember = optionalMember.get();
            existingMember.setFirstName(updatedMember.getFirstName());
            existingMember.setLastName(updatedMember.getLastName());
            existingMember.setAddress(updatedMember.getAddress());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setPhone(updatedMember.getPhone());
            existingMember.setDateOfBirth(updatedMember.getDateOfBirth());

            return ResponseEntity.status(HttpStatus.OK).body(memberRepository.save(existingMember));
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