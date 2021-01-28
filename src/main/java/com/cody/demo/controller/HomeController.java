package com.cody.demo.controller;

import com.cody.demo.entity.BackUserEntity;
import com.cody.demo.entity.PcUserEntity;
import com.cody.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PostMapping("addPcUser")
    public void addPcUser(@RequestBody PcUserEntity user) {
        homeService.addPcUser(user);
    }

    @PostMapping("addBackUser")
    public void addBackUser(BackUserEntity user) {
        homeService.addBackUser(user);
    }


}
