#!/bin/bash

until mysqladmin ping -h"mysql" -u"root" -p"1111" --silent; do
    echo "Waiting for MySQL to be ready..."
    sleep 1
done

mysql -h"mysql" -u"root" -p"1111" -e "CREATE DATABASE IF NOT EXISTS ox;"

echo "Schema 'ox' created successfully."
