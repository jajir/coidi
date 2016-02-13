/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.view.junit;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.op.view.entities.User;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.services.impl.UserServiceImpl;
import com.coroptis.coidi.op.view.util.BaseJunitTest;

/**
 * Tests for {@link UserServiceImpl}.
 * 
 * @author jirout
 * 
 */
public class UserServiceTest extends BaseJunitTest {

    private final static String SERVICE_NAME = "realService";

    private UserService service;

    private User user;

    public void testLogin() throws Exception {
	MessageDigest md = MessageDigest.getInstance("MD5");
	md.update("monkey".getBytes("UTF-8"));
	md.update(UserServiceImpl.SALT.getBytes());
	byte[] bytes = md.digest();
	EasyMock.expect(services.getConvertorService().convertToString(EasyMock.aryEq(bytes)))
		.andReturn("hashedPasswd");
	EasyMock.expect(services.getUserDao().login("karel", "hashedPasswd")).andReturn(user);
	services.replay();
	User ret = service.login("karel", "monkey");

	assertNotNull(ret);
	assertSame(user, ret);
	services.verify();
    }

    public void testGeneratePassword() throws Exception {
	System.out.println("qwe: '" + md5("qwe") + "'");
	System.out.println("asd: '" + md5("asd") + "'");
    }

    /**
     * Return Base64 encoded MD5 from input password.
     * 
     * @param plainPassword
     * @return
     */
    private String md5(String plainPassword) throws Exception {
	MessageDigest md = MessageDigest.getInstance("MD5");
	md.update(plainPassword.getBytes("UTF-8"));
	md.update(UserServiceImpl.SALT.getBytes());
	return new String(Base64.encodeBase64(md.digest()));
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(UserService.class, UserServiceImpl.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, UserService.class);
	user = new User();
	user.setIdUser(3);
	user.setName("karel");
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	user = null;
	super.tearDown();
    }
}
