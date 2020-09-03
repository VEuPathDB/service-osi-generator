INSERT INTO
  osi.id_set_collections (name, created_by)
VALUES
  (?, ?)
RETURNING
  id_set_coll_id
, created
;
