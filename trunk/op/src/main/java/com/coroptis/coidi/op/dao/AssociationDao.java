package com.coroptis.coidi.op.dao;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.coroptis.coidi.op.entities.AssociationImpl;

public interface AssociationDao {

	@CommitAfter
	void create(AssociationImpl association);

	AssociationImpl getByAssocHandle(String assoc_handle);

}
