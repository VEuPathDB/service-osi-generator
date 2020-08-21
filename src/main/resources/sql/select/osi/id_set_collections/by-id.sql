SELECT
  id_set_coll_id
, name
, created_by
, created
FROM
  osi.id_set_collections
WHERE
  id_set_coll_id = ?
;
