INSERT INTO
  osi.organisms (
    template
    , gene_counter_start
    , gene_counter_current
    , transcript_counter_start
    , transcript_counter_current
    , created_by
    , created
    , modified
  )
VALUES
  (
    ? -- 1. template
  , ? -- 2. gene_counter_start
  , ? -- 3. gene_counter_current
  , ? -- 4. transcript_counter_start
  , ? -- 5. transcript_counter_current
  , ? -- 6. created_by
  , now()
  , now()
  )
RETURNING
  organism_id
, created
, modified
;
