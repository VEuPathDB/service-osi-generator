SELECT
  id_set_id
, id_set_coll_id
, organism_id
, template
, created
, created_by
FROM
  osi.id_sets
WHERE
  id_set_coll_id IN (SELECT unnest(?::BIGINT[]))
;
