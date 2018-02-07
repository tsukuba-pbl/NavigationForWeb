#!/bin/bash

set -e

host="$1"
port="$2"

until nc -vz $host $port; do
  >&2 echo "MySQL is unavailable - sleeping"
  sleep 1
done

>&2 echo "MySQL is up - executing command"