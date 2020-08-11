FROM postgres:12-alpine

COPY ddl/postgres.sql /postgres.sql
COPY docker/db-init.sh /docker-entrypoint-initdb.d/init.sh
