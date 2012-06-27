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
package com.coroptis.coidi.test;

import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * Class that implement this interface can be executed during binding of T5
 * startup. Implementing class have to be setted into
 * {@link AdditionalBinderProvider} class.
 * 
 * @author jan
 * 
 */
public interface AdditionalBinder {

	void bind(ServiceBinder binder);

}
