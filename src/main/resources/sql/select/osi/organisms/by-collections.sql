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
  o.organism_id
, o.name
, o.template
, o.gene_counter_start
, o.gene_counter_current
, o.transcript_counter_start
, o.transcript_counter_current
, o.created_by
, o.created
, o.modified
, u.user_id
, u.user_name
, u.api_key
, u.issued
FROM
  osi.organisms         AS o
  INNER JOIN auth.users AS u
  ON o.created_by = u.user_id
WHERE
  o.organism_id IN (SELECT organism_id FROM sets)
