package com.cody.demo.dao;

import com.cody.demo.entity.PcUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcUserDao extends JpaRepository<PcUserEntity, Long> {

    PcUserEntity findByPhone(String phone);

}
