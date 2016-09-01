package fr.unice.polytech.mundus.data;

import java.io.Serializable;

/**
 * Created by spong on 2016/8/18.
 * @author WANG Yijie
 * @author TANG Yi
 */
public class User implements Serializable {

    String name;
    String mail;
    String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
