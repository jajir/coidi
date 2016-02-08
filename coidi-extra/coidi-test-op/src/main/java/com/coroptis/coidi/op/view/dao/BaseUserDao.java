package com.coroptis.coidi.op.view.dao;

import com.coroptis.coidi.op.view.entities.UserImpl;

public interface BaseUserDao {

    UserImpl getById(final Integer idUser);
    
}
