INSERT INTO
  osi.id_sets (
    id_set_coll_id
  , organism_id
  , template
  , counter_start
  , num_issued
  , created_by
  )
VALUES
  (?, ?, ?, ?, ?, ?)
RETURNING
  id_set_id
, created
;
