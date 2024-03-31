#!/bin/bash

HOST="localhost"
PORT="27017"
DATABASE="order-xpress"
ADMIN_USER="admin"
ADMIN_PASSWORD="1111"

COLLECTIONS=("outerwear" "socks")

for COLLECTION in "${COLLECTIONS[@]}";
do
    docker exec ox-mongodb mongoimport \
        --host "$HOST" --port "$PORT" \
        -u "$ADMIN_USER" -p "$ADMIN_PASSWORD" \
        --authenticationDatabase admin \
        --db "$DATABASE" --collection "$COLLECTION" \
        --type json --jsonArray \
        --file "${COLLECTION}.json"
done

echo "Data import completed successfully."
