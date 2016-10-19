package com.flairbit.example.cassandra.impl;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = Message.TABLE_NAME)
public class Message {

	public static final String TABLE_NAME = "kvs";
	
	private UUID key;
	private String message;
	
	public Message() {};
	
	public Message(String message) {
		this.key = UUID.randomUUID();
		this.message = message;
	};
	
	@PartitionKey(0)
	@Column(name="key")
	public UUID getKey() {
		return key;
	}
	public void setKey(UUID key) {
		this.key = key;
	}
	
	@Column(name="value")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
