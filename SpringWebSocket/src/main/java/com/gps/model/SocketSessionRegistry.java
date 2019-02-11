package com.gps.model;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.util.Assert;

/**
 * Objeto que registra sessao do usuario
 * @author guilherme sena
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SocketSessionRegistry {
	
	private final ConcurrentMap<String, Set<String>> userSessionIds = new ConcurrentHashMap();
	
	private final Object lock = new Object();

	public SocketSessionRegistry() {
	}

	public Set<String> getSessionIds(String user) {
		Set set = (Set)this.userSessionIds.get(user);
		return set != null?set: Collections.emptySet();
	}

	public ConcurrentMap<String, Set<String>> getAllSessionIds() {
		return this.userSessionIds;
	}

	public void registerSessionId(String user, String sessionId) {
		Assert.notNull(user, "Usuário não pode ser nulo");
		Assert.notNull(sessionId, "Id da sessão não pode ser nulo");
		synchronized(this.lock) {
			Object set = (Set)this.userSessionIds.get(user);
			if(set == null) {
				set = new CopyOnWriteArraySet();
				this.userSessionIds.put(user, (Set<String>) set);
			}else {
				this.userSessionIds.remove(user);
				set = new CopyOnWriteArraySet();
				this.userSessionIds.put(user,(Set<String>)set);
			}

			((Set)set).add(sessionId);
		}
	}

	public void unregisterSessionId(String userName, String sessionId) {
		Assert.notNull(userName, "Usuário não pode ser nulo");
		Assert.notNull(sessionId, "Id da sessão não pode ser nulo");
		synchronized(this.lock) {
			Set set = (Set)this.userSessionIds.get(userName);
			if(set != null && set.remove(sessionId) && set.isEmpty()) {
				this.userSessionIds.remove(userName);
			}

		}
	}
}
