package com.coroptis.coidi.op.services;

import java.util.Map;

import com.coroptis.coidi.op.util.RealmRequest;

public interface RealmTool {

    final static String KEY_IS_WILD_CARD_IN_REALM_ENABLED = "op.wild-card.in.realm.enabled";

    boolean isMatching(String realmPattern, String returnTo);

    RealmRequest createRealmRequest(Map<String, String> parameters);
}
