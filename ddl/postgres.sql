-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║                                                                        ║ --
-- ║    Initial Setup                                                       ║ --
-- ║                                                                        ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE USER osi_service LOGIN PASSWORD '${DB_PASSWORD}';
GRANT USAGE ON LANGUAGE plpgsql TO osi_service;

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║                                                                        ║ --
-- ║    User Authentication Schema                                          ║ --
-- ║                                                                        ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Create Schema                                                       ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE SCHEMA auth;


-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Users Table                                                         ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE TABLE auth.users
(
  -- Primary key value
  user_id   BIGSERIAL PRIMARY KEY,
  user_name VARCHAR     NOT NULL
    CHECK (length(user_name) >= 3),
  api_key   VARCHAR     NOT NULL UNIQUE
    CHECK (length(api_key) >= 32),
  issued    TIMESTAMPTZ NOT NULL
    DEFAULT now()
);

CREATE UNIQUE INDEX user_name_uniq_ind ON auth.users (LOWER(user_name));

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Permissions                                                         ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

GRANT USAGE ON SCHEMA "auth" TO osi_service;
GRANT INSERT, SELECT ON ALL TABLES IN SCHEMA auth TO osi_service;
GRANT USAGE, SELECT ON auth.users_user_id_seq TO osi_service;

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║                                                                        ║ --
-- ║    Organism ID Service Schema                                          ║ --
-- ║                                                                        ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Create Schema                                                       ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE SCHEMA osi;

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Organisms Table                                                     ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE TABLE osi.organisms
(
  organism_id                BIGSERIAL PRIMARY KEY,
  name                       VARCHAR(32) NOT NULL
    CHECK (length(name) >= 3),
  template                   VARCHAR(32) NOT NULL UNIQUE
    CHECK (length(template) >= 5 AND template ~ '^.*%[0-9,+\- $.]*d.*$'),
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
  created                    TIMESTAMPTZ NOT NULL
                                                  DEFAULT now(),
  modified                   TIMESTAMPTZ NOT NULL
                                                  DEFAULT now()
);

CREATE UNIQUE INDEX org_name_uniq_ind ON osi.organisms (LOWER(name));

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Collections Table                                                   ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE TABLE osi.id_set_collections
(
  id_set_coll_id BIGSERIAL PRIMARY KEY,
  name           VARCHAR     NOT NULL
    CHECK (length(name) >= 3),
  created_by     BIGINT      NOT NULL
    REFERENCES auth.users (user_id),
  created        TIMESTAMPTZ NOT NULL
    DEFAULT now()
);

CREATE UNIQUE INDEX coll_name_uniq_ind ON osi.id_set_collections (LOWER(name));

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    ID Sets Table                                                       ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE TABLE osi.id_sets
(
  id_set_id      BIGSERIAL PRIMARY KEY,
  id_set_coll_id BIGINT      NOT NULL
    REFERENCES osi.id_set_collections (id_set_coll_id),
  organism_id    BIGINT      NOT NULL
    REFERENCES osi.organisms (organism_id),
  template       VARCHAR(32) NOT NULL
    CHECK (length(template) >= 5 AND template ~ '^.*%[0-9,+\- $.]*d.*$'),
  counter_start  BIGINT      NOT NULL
    CHECK (counter_start >= 1),
  num_issued     INT         NOT NULL
    CHECK (num_issued >= 0),
  created        TIMESTAMPTZ NOT NULL
    DEFAULT now(),
  created_by     BIGINT      NOT NULL
    REFERENCES auth.users (user_id)
);

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Genes Table                                                         ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE TABLE osi.genes
(
  gene_id         BIGSERIAL PRIMARY KEY,
  id_set_id       BIGINT      NOT NULL
    REFERENCES osi.id_sets (id_set_id),
  gene_identifier VARCHAR     NOT NULL
    CHECK (length(gene_identifier) >= 4),
  created         TIMESTAMPTZ NOT NULL
    DEFAULT now(),
  created_by      BIGINT      NOT NULL
    REFERENCES auth.users (user_id)
);

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Transcripts Table                                                   ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

CREATE TABLE osi.transcripts
(
  transcript_id BIGSERIAL PRIMARY KEY,
  gene_id       BIGINT      NOT NULL
    REFERENCES osi.genes (gene_id),
  counter_start BIGINT      NOT NULL
    CHECK (counter_start >= 1),
  num_issued    INT         NOT NULL
    CHECK (num_issued >= 0),
  created       TIMESTAMPTZ NOT NULL
    DEFAULT now(),
  created_by    BIGINT      NOT NULL
    REFERENCES auth.users (user_id)
);

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Procedures                                                          ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

-- Verify that the given `old_start` and `old_curr` values are currently equal.
--
-- This is to enforce that the starting value cannot be changed once the current
-- value has been incremented (meaning IDs have been issued).
CREATE FUNCTION organism_validate_start_update() RETURNS TRIGGER AS
'
  BEGIN
    IF (new.gene_counter_start != old.gene_counter_start
      AND old.gene_counter_start != old.gene_counter_current) THEN
      RAISE EXCEPTION
        USING MESSAGE = ''Cannot update the gene start counter once IDs have been issued.'';
    END IF;

    IF (new.transcript_counter_start != old.transcript_counter_start
      AND old.transcript_counter_start != old.transcript_counter_current) THEN
      RAISE EXCEPTION
        USING MESSAGE = ''Cannot update the transcript start counter once IDs have been issued.'';
    END IF;

    RETURN new;
  END;
' LANGUAGE plpgsql;

-- Verify that the given `new_curr` counter value is not less than the previous
-- counter value _IF_ the `old_curr` value has already been incremented.
--
-- This is to enforce that the current value cannot be set to something lower
-- than it's prior value once the start and current values have diverged
-- (meaning IDs have been issued).
CREATE FUNCTION organism_validate_curr_update() RETURNS TRIGGER AS
'
  BEGIN
    IF (old.gene_counter_start != old.gene_counter_current
      AND new.gene_counter_current < old.gene_counter_current)
    THEN
      RAISE EXCEPTION
        USING MESSAGE = ''Cannot reduce the current gene id '' ||
                        ''counter value once IDs have been issued.'';
    END IF;

    IF (old.transcript_counter_start != old.transcript_counter_current
      AND new.transcript_counter_current < old.transcript_counter_current)
    THEN
      RAISE EXCEPTION
        USING MESSAGE = ''Cannot reduce the current transcript id '' ||
                        ''counter value once IDs have been issued.'';
    END IF;

    RETURN new;
  END;
' LANGUAGE plpgsql;

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Triggers                                                            ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

-- Database level record integrity enforcement on counter starting values.
--
-- A counter starting point cannot be updated once IDs have been issued.
CREATE TRIGGER organism_transcript_start_update
  BEFORE UPDATE
  ON osi.organisms
  FOR EACH ROW
EXECUTE PROCEDURE organism_validate_start_update();

-- Database level record integrity enforcement on counter current values.
--
-- The counter current value cannot be set to a lower value once IDs have been
-- issued.
CREATE TRIGGER organism_transcript_current_update
  BEFORE UPDATE
  ON osi.organisms
  FOR EACH ROW
EXECUTE PROCEDURE organism_validate_curr_update();

-- ╔════════════════════════════════════════════════════════════════════════╗ --
-- ║    Permissions                                                         ║ --
-- ╚════════════════════════════════════════════════════════════════════════╝ --

-- Access to the schema
GRANT USAGE ON SCHEMA "osi" TO osi_service;

-- INSERT and SELECT are universally allowed in the osi schema
GRANT INSERT, SELECT ON ALL TABLES IN SCHEMA osi TO osi_service;

-- Updates are only allowed on the organisms table and the `name` column of the
-- collections table.
GRANT UPDATE ON osi.organisms TO osi_service;
GRANT UPDATE (name) ON osi.id_set_collections TO osi_service;

-- Permissions needed for the validation triggers
GRANT EXECUTE ON FUNCTION organism_validate_start_update() TO osi_service;
GRANT EXECUTE ON FUNCTION organism_validate_curr_update() TO osi_service;

-- Permissions needed for the primary key sequences.
GRANT USAGE, SELECT ON
  osi.genes_gene_id_seq
  , osi.id_set_collections_id_set_coll_id_seq
  , osi.id_sets_id_set_id_seq
  , osi.organisms_organism_id_seq
  , osi.transcripts_transcript_id_seq
  TO osi_service;
