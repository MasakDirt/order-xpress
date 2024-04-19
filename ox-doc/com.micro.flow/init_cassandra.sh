#!/bin/bash

CQL="
CREATE KEYSPACE IF NOT EXISTS ox_bag WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};
"

until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable - retry later"
  sleep 2
done &

exec /usr/local/bin/docker-entrypoint.sh