package com.ekstedt.individuelllab.controllers;

import com.ekstedt.individuelllab.entities.Member;
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
        return memberServices.addMember(member);
    }


    @RequestMapping(value = "/admin/updatemember/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        return memberServices.updateMember(id, updatedMember);
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