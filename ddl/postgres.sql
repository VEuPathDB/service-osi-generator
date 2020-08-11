CREATE SCHEMA osi;

CREATE TABLE osi.organisms (
  organism_id          SERIAL PRIMARY KEY,
  template             VARCHAR(16) NOT NULL UNIQUE,
  gene_counter_start   BIGINT      NOT NULL DEFAULT 1 CHECK (gene_counter_start >= 1),
  gene_counter_current BIGINT      NOT NULL DEFAULT 1 CHECK (gene_counter_current >= gene_counter_start),
  created              TIMESTAMPTZ NOT NULL DEFAULT now(),
  modified             TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE osi.gene_sets (
  gene_set_id SERIAL PRIMARY KEY,
  organism_id BIGINT      NOT NULL REFERENCES osi.organisms (organism_id),
  template    VARCHAR(16) NOT NULL,
  created     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE osi.genes (
  gene_id            SERIAL PRIMARY KEY,
  gene_set_id        INT     NOT NULL REFERENCES osi.gene_sets (gene_set_id),
  gene_identifier    VARCHAR NOT NULL,
  transcript_counter INT     NOT NULL DEFAULT 1 CHECK (transcript_counter >= 1)
);

CREATE TABLE osi.transcripts (
  gene_id       INT         NOT NULL REFERENCES osi.genes (gene_id),
  counter_start BIGINT      NOT NULL,
  num_issued    INT         NOT NULL,
  created       TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE SCHEMA auth;

CREATE TABLE auth.api_keys (
  user_email VARCHAR     NOT NULL UNIQUE,
  api_key    VARCHAR     NOT NULL UNIQUE,
  issued     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE USER osi_service LOGIN PASSWORD '${DB_PASSWORD}';
GRANT INSERT, UPDATE, SELECT ON ALL TABLES IN SCHEMA osi TO osi_service;
GRANT INSERT, UPDATE, SELECT ON ALL TABLES IN SCHEMA auth TO osi_service;
