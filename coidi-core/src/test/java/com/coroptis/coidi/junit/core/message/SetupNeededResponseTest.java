package com.coroptis.coidi.junit.core.message;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.SetupNeededResponse;

public class SetupNeededResponseTest {

	Logger logger = LoggerFactory.getLogger(SetupNeededResponseTest.class);

	@Test
	public void test_messageText() throws Exception {
		SetupNeededResponse msg = new SetupNeededResponse(AbstractMessage.OPENID_NS_20, "http://localost/test-rp/");

		assertTrue(msg.isUrl());
		String link = msg.getUrl();
		logger.debug(link);
		assertTrue(msg.getMessage().contains("mode:setup_needed"));
	}

	@Test(expected = NullPointerException.class)
	public void test_returnTo_isRequired() throws Exception {
		new SetupNeededResponse(AbstractMessage.OPENID_NS_20, null);
	}
	

}
