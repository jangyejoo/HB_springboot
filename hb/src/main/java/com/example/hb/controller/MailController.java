package com.example.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hb.models.MailTO;
import com.example.hb.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {
	@Autowired
    private MailService mailService;

    @GetMapping("/send")
    public MailTO sendTestMail(String email, String pwd) {
        MailTO mailTO = new MailTO();
        System.out.println(pwd);
        mailTO.setAddress(email);
        mailTO.setTitle("[Healthy Buddy] 비밀번호 찾기");
        mailTO.setMessage("비밀번호는 "+pwd+" 입니다.");

        mailService.sendMail(mailTO);

        return mailTO;
    }
}
