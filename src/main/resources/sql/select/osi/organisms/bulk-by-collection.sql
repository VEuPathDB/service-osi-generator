WITH
  sets AS (
    SELECT
      organism_id
    FROM
      osi.id_sets
    WHERE
      id_set_coll_id IN (unnest(?::INT[]))
  )
SELECT
  organism_id
, template
, gene_counter_start
, gene_counter_current
, transcript_counter_start
, transcript_counter_current
, created_by
, created
, modified
FROM
  osi.organisms
WHERE
  organism_id IN (SELECT organism_id FROM sets)
