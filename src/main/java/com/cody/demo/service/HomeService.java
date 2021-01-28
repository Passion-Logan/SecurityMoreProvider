package com.cody.demo.service;

import com.cody.demo.entity.BackUserEntity;
import com.cody.demo.entity.PcUserEntity;

public interface HomeService {

    void addPcUser(PcUserEntity user);

    void addBackUser(BackUserEntity user);

}
