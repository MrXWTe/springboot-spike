package cn.mrxu.spike.domain;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;



    /********************************constructor******************************************/

    public User() {
    }



    /********************************get&set******************************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
