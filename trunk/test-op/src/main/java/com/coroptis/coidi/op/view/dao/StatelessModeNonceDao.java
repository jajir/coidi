package com.coroptis.coidi.op.view.dao;

import com.coroptis.coidi.op.view.entities.StatelessModeNonce;

public interface StatelessModeNonceDao {

	void save(StatelessModeNonce statelessModeNonce);

	StatelessModeNonce getByNonce(String noce);

}
