package com.coroptis.coidi.op.view.services;

import java.util.Date;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.coroptis.coidi.op.view.entities.AssociationImpl;

public interface AssociationService {

	@CommitAfter
	void create(AssociationImpl association);

	/**
	 * Generate time to live for currently creates association handle.
	 * 
	 * @return
	 */
	Date getTimeToLive();

	AssociationImpl getByAssocHandle(String assoc_handle);

}
