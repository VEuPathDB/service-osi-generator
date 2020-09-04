CREATE SCHEMA auth;

CREATE TABLE auth.users
(
  user_id   BIGSERIAL PRIMARY KEY,
  user_name VARCHAR     NOT NULL,
  api_key   VARCHAR     NOT NULL UNIQUE,
  issued    TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX user_name_uniq_ind ON auth.users (LOWER(user_name));

CREATE SCHEMA osi;

CREATE TABLE osi.organisms
(
  organism_id                BIGSERIAL PRIMARY KEY,
  name                       VARCHAR(32) NOT NULL,
  template                   VARCHAR(16) NOT NULL UNIQUE,
  gene_counter_start         BIGINT      NOT NULL DEFAULT 1
    CHECK (gene_counter_start >= 1),
  gene_counter_current       BIGINT      NOT NULL DEFAULT 1
    CHECK (gene_counter_current >= gene_counter_start),
  transcript_counter_start   BIGINT      NOT NULL DEFAULT 1
    CHECK (transcript_counter_start >= 1),
  transcript_counter_current BIGINT      NOT NULL DEFAULT 1
    CHECK (transcript_counter_current >= transcript_counter_start),
  created_by                 BIGINT      NOT NULL
    REFERENCES auth.users (user_id),
  created                    TIMESTAMPTZ NOT NULL DEFAULT now(),
  modified                   TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX org_name_uniq_ind ON osi.organisms (LOWER(name));

CREATE TABLE osi.id_set_collections
(
  id_set_coll_id BIGSERIAL PRIMARY KEY,
  name           VARCHAR     NOT NULL,
  created_by     BIGINT      NOT NULL REFERENCES auth.users (user_id),
  created        TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX coll_name_uniq_ind ON osi.id_set_collections (LOWER(name));

CREATE TABLE osi.id_sets
(
  id_set_id      BIGSERIAL PRIMARY KEY,
  id_set_coll_id BIGINT      NOT NULL
    REFERENCES osi.id_set_collections (id_set_coll_id),
  organism_id    BIGINT      NOT NULL REFERENCES osi.organisms (organism_id),
  template       VARCHAR(16) NOT NULL,
  counter_start  BIGINT      NOT NULL,
  num_issued     INT         NOT NULL CHECK ( num_issued >= 0 ),
  created        TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_by     BIGINT      NOT NULL REFERENCES auth.users (user_id)
);

CREATE TABLE osi.genes
(
  gene_id         BIGSERIAL PRIMARY KEY,
  id_set_id       BIGINT      NOT NULL REFERENCES osi.id_sets (id_set_id),
  gene_identifier VARCHAR     NOT NULL,
  created         TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_by      BIGINT      NOT NULL REFERENCES auth.users (user_id)
);

CREATE TABLE osi.transcripts
(
  transcript_id BIGSERIAL PRIMARY KEY,
  gene_id       BIGINT      NOT NULL REFERENCES osi.genes (gene_id),
  counter_start BIGINT      NOT NULL,
  num_issued    INT         NOT NULL,
  created       TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_by    BIGINT      NOT NULL REFERENCES auth.users (user_id)
);

CREATE USER osi_service LOGIN PASSWORD '${DB_PASSWORD}';
GRANT USAGE ON SCHEMA "osi" TO osi_service;
GRANT USAGE ON SCHEMA "auth" TO osi_service;

GRANT USAGE, SELECT ON
  auth.users_user_id_seq
, osi.genes_gene_id_seq
, osi.id_set_collections_id_set_coll_id_seq
, osi.id_sets_id_set_id_seq
, osi.organisms_organism_id_seq
, osi.transcripts_transcript_id_seq
TO osi_service;

GRANT INSERT, SELECT ON ALL TABLES IN SCHEMA osi  TO osi_service;
GRANT INSERT, SELECT ON ALL TABLES IN SCHEMA auth TO osi_service;

GRANT UPDATE (
  template
, gene_counter_start
, gene_counter_current
, transcript_counter_start
, transcript_counter_current
, modified
) ON osi.organisms TO osi_service;

GRANT UPDATE (name) ON osi.id_set_collections TO osi_service;
