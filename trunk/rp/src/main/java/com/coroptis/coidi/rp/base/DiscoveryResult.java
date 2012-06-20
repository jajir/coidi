package com.coroptis.coidi.rp.base;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.coroptis.coidi.CoidiException;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

public class DiscoveryResult {

	private final Set<XrdService> services = new HashSet<XrdService>();

	private final ImmutableSortedSet.Builder<XrdService> sortedServicesBuilder = new ImmutableSortedSet.Builder<XrdService>(
			new Comparator<XrdService>() {
				@Override
				public int compare(XrdService service1, XrdService service2) {
					if (service1.idPresent(XrdService.TYPE_OPENID_2_0)) {
						if (service2.idPresent(XrdService.TYPE_OPENID_2_0)) {
							if (service1
									.idPresent(XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
								if (service2
										.idPresent(XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
									return service1.getEffectivePriority()
											- service2.getEffectivePriority();
								} else {
									return 1;
								}
							} else {
								if (service2
										.idPresent(XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
									return -1;
								} else {
									return 0;
								}
							}
						} else {
							return 1;
						}
					} else {
						if (service2.idPresent(XrdService.TYPE_OPENID_2_0)) {
							return -1;
						} else {
							if (service1
									.idPresent(XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
								if (service2
										.idPresent(XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
									return service1.getEffectivePriority()
											- service2.getEffectivePriority();
								} else {
									return 1;
								}
							} else {
								if (service2
										.idPresent(XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
									return -1;
								} else {
									throw new CoidiException(
											"both services are not OpenID 2.0 compatible");
								}
							}
						}
					}
				}
			});

	public XrdService getPreferedService() {
		UnmodifiableIterator<XrdService> openId20Services = Iterators.filter(
				services.iterator(), new Predicate<XrdService>() {
					@Override
					public boolean apply(XrdService xrdService) {
						return xrdService
								.idPresent(XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)
								|| xrdService
										.idPresent(XrdService.TYPE_OPENID_2_0);
					}
				});
		ImmutableSortedSet<XrdService> out = sortedServicesBuilder.addAll(
				openId20Services).build();
		return out.size() == 0 ? null : out.first();
	}

	/**
	 * @return the endPoint
	 */
	public String getEndPoint() {
		return getPreferedService() == null ? null : getPreferedService()
				.getUrl();
	}

	/**
	 * @return the services
	 */
	public Set<XrdService> getServices() {
		return services;
	}

}
