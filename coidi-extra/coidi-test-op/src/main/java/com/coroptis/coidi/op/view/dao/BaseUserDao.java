package com.coroptis.coidi.op.view.dao;

import com.coroptis.coidi.op.view.entities.User;

public interface BaseUserDao {

    User getById(final Integer idUser);
    
}
