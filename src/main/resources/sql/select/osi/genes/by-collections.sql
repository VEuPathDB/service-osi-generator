WITH
  sets AS (
    SELECT
      id_set_id
    FROM
      osi.id_sets
    WHERE
      id_set_coll_id IN (unnest(?::BIGINT[]))
  )
SELECT
  *
FROM
  osi.genes
WHERE
  id_set_id IN (SELECT id_set_id FROM sets)
;
