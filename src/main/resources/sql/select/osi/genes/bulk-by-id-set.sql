SELECT
  *
FROM
  osi.genes
WHERE
  id_set_id IN (unnest(?::INT[]))
;
