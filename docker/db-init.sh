#!/usr/bin/env sh

if [ -z "${DB_PASSWORD}" ]; then
  echo 'Cannot start Postgres.  Missing required environment variable $DB_PASSWORD'
  exit 1
fi

psql -v ON_ERROR_STOP=1 --username "${POSTGRES_USER}" --dbname "${POSTGRES_DB}" <<-EOF
$(eval "echo $(cat /postgres.sql)")
EOF
