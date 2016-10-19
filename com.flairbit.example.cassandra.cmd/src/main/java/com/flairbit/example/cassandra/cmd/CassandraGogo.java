package com.flairbit.example.cassandra.cmd;

import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osgi.enroute.debug.api.Debug;
import com.flairbit.example.cassandra.api.Cassandra;

@Component(property= { 
		Debug.COMMAND_SCOPE+"=cassandra", 
		Debug.COMMAND_FUNCTION+"=store" },
		service=CassandraGogo.class)
public class CassandraGogo {

	@Reference 
	Cassandra cassandra;
	
	public UUID store(String message) {
		return cassandra.trace(message);
	}
	
}
