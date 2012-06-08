package com.coroptis.coidi.op.dao;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.coroptis.coidi.op.entities.StatelessModeNonce;

public interface StatelessModeNonceDao {

	@CommitAfter
	void save(StatelessModeNonce statelessModeNonce);

	StatelessModeNonce getByNonce(String noce);

}
