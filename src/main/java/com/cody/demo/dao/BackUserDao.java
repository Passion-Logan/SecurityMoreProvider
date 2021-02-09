package com.cody.demo.dao;

import com.cody.demo.entity.BackUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackUserDao extends JpaRepository<BackUserEntity, Long> {
    BackUserEntity findByPhone(String phone);
}
