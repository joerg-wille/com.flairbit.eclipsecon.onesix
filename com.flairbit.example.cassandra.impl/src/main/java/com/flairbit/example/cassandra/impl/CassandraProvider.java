package com.flairbit.example.cassandra.impl;

import java.util.Optional;
import java.util.UUID;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.flairbit.example.cassandra.api.Cassandra;

@Component(name = "com.flairbit.example.cassandra", immediate=true)
public class CassandraProvider implements Cassandra {

	private final static String KEYSPACE = "eclipsecon";
	private final static String CREATE_KS_CQL = "CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE + 
			" WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};";
	private final static String CREATE_TABLE_CQL = "CREATE TABLE " + KEYSPACE +".kvs (key UUID PRIMARY KEY, value TEXT);";
	
	@Reference
	private LogService log;

	private Session session;
	private Mapper<Message> mapper;

	@Activate
	public void activate() {
		try {
			Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
			KeyspaceMetadata meta = cluster.getMetadata().getKeyspace(KEYSPACE);
			if (meta == null) {
				try (Session s = cluster.connect()){
					s.execute(CREATE_KS_CQL);
					s.execute(CREATE_TABLE_CQL);
				}
			}
			session = cluster.connect(KEYSPACE);
			MappingManager mappingManager = new MappingManager(session);
			mapper = mappingManager.mapper(Message.class);
		} catch (Throwable tr) {
			tr.printStackTrace(System.err);
			if (session != null) {
				session.close();
			}
		}
	}

	@Deactivate
	public void deactivate() {
		session.close();
	}

	@Override
	public UUID trace(String message) {
		Message entity = new Message(message);
		mapper.save(entity);
		return entity.getKey();
	}

	@Override
	public String getMessage(UUID key) {
		return Optional.ofNullable(mapper.get(key)).
				map(Message::getMessage).orElse(null);
	}

}
