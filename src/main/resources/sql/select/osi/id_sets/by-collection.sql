SELECT
  id_set_id
, id_set_coll_id
, organism_id
, template
, counter_start
, num_issued
, created
, created_by
FROM
  osi.id_sets AS s
WHERE
  id_set_coll_id = ?
;
