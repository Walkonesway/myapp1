package com.example.testone;

import java.io.Serializable;

public class User implements Serializable{
    String name;
    String permission;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    @Override
    public String toString() {
        return "user [name=" + name + ", permission=" + permission + "]";
    }
    public User(String name, String permission) {
        super();
        this.name = name;
        this.permission = permission;
    }

}
