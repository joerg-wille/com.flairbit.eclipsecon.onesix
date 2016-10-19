package com.flairbit.example.cassandra.api;

import java.util.UUID;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Cassandra {
	
	UUID trace(String message);
	
	String getMessage(UUID key);
}
