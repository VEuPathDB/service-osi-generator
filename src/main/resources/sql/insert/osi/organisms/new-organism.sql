INSERT INTO
  osi.organisms (
    name
    , template
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
    ? -- 1. name
  , ? -- 2. template
  , ? -- 3. gene_counter_start
  , ? -- 4. gene_counter_current
  , ? -- 5. transcript_counter_start
  , ? -- 6. transcript_counter_current
  , ? -- 7. created_by
  , now()
  , now()
  )
RETURNING
  organism_id
, created
, modified
;
