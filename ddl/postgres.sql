CREATE SCHEMA auth;

CREATE TABLE auth.users
(
  user_id   SERIAL PRIMARY KEY,
  user_name VARCHAR     NOT NULL UNIQUE,
  api_key   VARCHAR     NOT NULL UNIQUE,
  issued    TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE SCHEMA osi;

CREATE TABLE osi.organisms
(
  organism_id                SERIAL PRIMARY KEY,
  name                       VARCHAR(32) NOT NULL UNIQUE,
  template                   VARCHAR(16) NOT NULL UNIQUE,
  gene_counter_start         BIGINT      NOT NULL DEFAULT 1
    CHECK (gene_counter_start >= 1),
  gene_counter_current       BIGINT      NOT NULL DEFAULT 1
    CHECK (gene_counter_current >= gene_counter_start),
  transcript_counter_start   BIGINT      NOT NULL DEFAULT 1
    CHECK (transcript_counter_start >= 1),
  transcript_counter_current BIGINT      NOT NULL DEFAULT 1
    CHECK (transcript_counter_current >= transcript_counter_start),
  created_by                 INT         NOT NULL
    REFERENCES auth.users (user_id),
  created                    TIMESTAMPTZ NOT NULL DEFAULT now(),
  modified                   TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE osi.id_set_collections
(
  id_set_coll_id SERIAL PRIMARY KEY,
  name           VARCHAR     NOT NULL UNIQUE,
  created_by     INT         NOT NULL REFERENCES auth.users (user_id),
  created        TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE osi.id_sets
(
  id_set_id      SERIAL PRIMARY KEY,
  id_set_coll_id INT         NOT NULL
    REFERENCES osi.id_set_collections (id_set_coll_id),
  organism_id    INT         NOT NULL
    REFERENCES osi.organisms (organism_id),
  template       VARCHAR(16) NOT NULL,
  counter_start  BIGINT      NOT NULL,
  num_issued     INT         NOT NULL
    CHECK ( num_issued >= 0 ),
  created        TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_by     INT         NOT NULL
    REFERENCES auth.users (user_id)
);

CREATE TABLE osi.genes
(
  gene_id         SERIAL PRIMARY KEY,
  id_set_id       INT         NOT NULL
    REFERENCES osi.id_sets (id_set_id),
  gene_identifier VARCHAR     NOT NULL,
  created         TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_by      INT         NOT NULL
    REFERENCES auth.users (user_id)
);

CREATE TABLE osi.transcripts
(
  gene_id       INT         NOT NULL
    REFERENCES osi.genes (gene_id),
  counter_start BIGINT      NOT NULL,
  num_issued    INT         NOT NULL,
  created       TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_by    INT         NOT NULL
    REFERENCES auth.users (user_id)
);

CREATE USER osi_service LOGIN PASSWORD '${DB_PASSWORD}';
GRANT USAGE ON SCHEMA "osi" TO osi_service;
GRANT USAGE ON SCHEMA "auth" TO osi_service;
GRANT INSERT, UPDATE, SELECT ON ALL TABLES IN SCHEMA osi TO osi_service;
GRANT INSERT, UPDATE, SELECT ON ALL TABLES IN SCHEMA auth TO osi_service;
