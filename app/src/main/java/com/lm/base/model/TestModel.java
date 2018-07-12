package com.lm.base.model;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2018/4/28.
 */

public class TestModel {
    public TestModel(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
