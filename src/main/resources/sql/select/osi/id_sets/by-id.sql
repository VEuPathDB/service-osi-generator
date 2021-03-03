SELECT
  id_set_id
, organism_id
, template
, counter_start
, num_issued
, created
, created_by
FROM
  osi.id_sets
WHERE
  id_set_id = ?
;
