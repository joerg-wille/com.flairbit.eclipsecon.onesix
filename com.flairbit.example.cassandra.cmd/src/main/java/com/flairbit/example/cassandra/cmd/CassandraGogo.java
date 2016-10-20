package com.flairbit.example.cassandra.cmd;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.flairbit.example.cassandra.api.Cassandra;

import osgi.enroute.debug.api.Debug;

@Component(property= { 
		Debug.COMMAND_SCOPE+"=cassandra", 
		Debug.COMMAND_FUNCTION+"=store" },
		service=CassandraGogo.class)
public class CassandraGogo {

	@Reference 
	Cassandra cassandra;
	
	public String store(String message) {
		return Optional.ofNullable(cassandra.trace(message))
				.map(String::valueOf).orElse(null);
	}
}
