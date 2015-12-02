package com.project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface AuthenticationAsync {
	void login(String login, int passwordHash, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
}
