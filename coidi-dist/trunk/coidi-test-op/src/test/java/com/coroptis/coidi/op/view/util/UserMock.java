package com.coroptis.coidi.op.view.util;

import java.util.Set;

import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.User;

public class UserMock implements User {

    private Integer idUser;

    private String name;

    private String password;

    private Set<Identity> identities;

    /**
     * @return the idUser
     */
    public Integer getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the identities
     */
    public Set<Identity> getIdentities() {
        return identities;
    }

    /**
     * @param identities the identities to set
     */
    public void setIdentities(Set<Identity> identities) {
        this.identities = identities;
    }

}
