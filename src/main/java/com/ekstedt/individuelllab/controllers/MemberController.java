package com.ekstedt.individuelllab.controllers;

import com.ekstedt.individuelllab.entities.Address;
import com.ekstedt.individuelllab.entities.Member;
import com.ekstedt.individuelllab.exceptions.ResourceNotFoundException;
import com.ekstedt.individuelllab.repositories.AddressRepository;
import com.ekstedt.individuelllab.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.ekstedt.individuelllab.services.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberServices memberServices;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(value = "/admin/members", method = RequestMethod.GET)
    @ResponseBody
    public List<Member> getAllMembers() {
        return memberServices.getAllMembers();
    }

    @ResponseBody
    @RequestMapping(value = "/admin/member/{id}", method = RequestMethod.GET)
    public Member getMemberById(@PathVariable Long id) {
        return memberServices.getMemberById(id);
    }


    @RequestMapping(value = "/admin/addmember", method = RequestMethod.POST)
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
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


    @RequestMapping(value = "/admin/updatemember/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        Member existingMember = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));

        existingMember.setFirstName(updatedMember.getFirstName());
        existingMember.setLastName(updatedMember.getLastName());
        existingMember.setAddress(updatedMember.getAddress());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setPhone(updatedMember.getPhone());
        existingMember.setDateOfBirth(updatedMember.getDateOfBirth());

        memberRepository.save(existingMember);

        return ResponseEntity.status(HttpStatus.OK).body(existingMember);
    }

    @RequestMapping(value = "/admin/deletemember/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMemberById(@PathVariable Long id) {
        memberServices.deleteMemberById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Member with ID " + id + " has been deleted successfully.");
    }


    @RequestMapping(value = "/admin/deletemember", method = RequestMethod.GET)
    public String getAllMembers(Model model) {
        model.addAttribute("members", memberServices.getAllMembers());
        return "admin/deletemember";
    }

    @RequestMapping(value = "/admin/deletemember/{id}", method = RequestMethod.GET)
    public String deleteMemberPage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        memberServices.deleteMemberById(id);
        redirectAttributes.addFlashAttribute("message", "Member with ID " + id + " has been deleted successfully.");
        return "redirect:/admin/deletemember";
    }
}