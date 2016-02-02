package com.coroptis.coidi.op.util;

import java.util.Date;
import java.util.Set;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;

/**
 * Mock implementation of {@link Association}.
 * 
 * @author jirout
 *
 */
public class AssociationMock implements Association {

	private String assocHandle;

	private AssociationType associationType;

	private SessionType sessionType;

	private Date expiredIn;

	private String macKey;

	private Set<Nonce> nonces;

	@Override
	public String getAssocHandle() {
		return assocHandle;
	}

	@Override
	public void setAssocHandle(String assocHandle) {
		this.assocHandle = assocHandle;
	}

	@Override
	public AssociationType getAssociationType() {
		return associationType;
	}

	@Override
	public void setAssociationType(AssociationType associationType) {
		this.associationType = associationType;
	}

	@Override
	public SessionType getSessionType() {
		return sessionType;
	}

	@Override
	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}

	@Override
	public Date getExpiredIn() {
		return expiredIn;
	}

	@Override
	public void setExpiredIn(Date date) {
		this.expiredIn = expiredIn;
	}

	@Override
	public String getMacKey() {
		return macKey;
	}

	@Override
	public void setMacKey(String macKey) {
		this.macKey = macKey;

	}

	@Override
	public Set<Nonce> getNonces() {
		return nonces;
	}

	@Override
	public void setNonces(Set<Nonce> nonces) {
		this.nonces = nonces;
	}

}
