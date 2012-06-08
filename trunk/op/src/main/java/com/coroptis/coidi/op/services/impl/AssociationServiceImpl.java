package com.coroptis.coidi.op.services.impl;

import java.util.Calendar;
import java.util.Date;

import com.coroptis.coidi.op.services.AssociationService;

public class AssociationServiceImpl implements AssociationService {

	@Override
	public Date getTimeToLive() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		return cal.getTime();
	}

}
