CREATE KEYSPACE IF NOT EXISTS eclipsecon WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE eclipsecon;

CREATE TABLE kvs (
    key UUID PRIMARY KEY,
    value TEXT
);