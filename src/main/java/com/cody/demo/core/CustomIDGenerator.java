package com.cody.demo.core;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class CustomIDGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        Object id = snowFlake.nextId();
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(s, obj);
    }
}
