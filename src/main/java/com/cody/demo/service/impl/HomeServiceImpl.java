package com.cody.demo.service.impl;

import com.cody.demo.dao.BackUserDao;
import com.cody.demo.dao.PcUserDao;
import com.cody.demo.entity.BackUserEntity;
import com.cody.demo.entity.PcUserEntity;
import com.cody.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private PcUserDao pcDao;

    @Autowired
    private BackUserDao backDao;

    @Override
    public void addPcUser(PcUserEntity user) {
        pcDao.save(user);
    }

    @Override
    public void addBackUser(BackUserEntity user) {
        backDao.save(user);
    }

}
