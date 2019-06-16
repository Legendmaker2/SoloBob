package com.codefair.solobob;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
