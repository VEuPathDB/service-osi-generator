WITH
  coll AS (
    SELECT
      id_set_coll_id
    , created_by
    FROM
      osi.id_set_collections
    WHERE
      id_set_coll_id = ?
  )
, sets AS (
    SELECT
      id_set_id
    , created_by
    FROM
      osi.id_sets
    WHERE
      id_set_coll_id IN (SELECT id_set_coll_id FROM coll)
  )
, genes AS (
    SELECT
      gene_id
    , created_by
    FROM
      osi.genes
    WHERE
      id_set_id IN (SELECT DISTINCT id_set_id FROM sets)
  )
, transcripts AS (
    SELECT
      created_by
    FROM
      osi.transcripts
    WHERE
      gene_id IN (SELECT DISTINCT gene_id FROM genes)
  )
, all_users AS (
    SELECT created_by FROM coll
    UNION
    SELECT created_by FROM sets
    UNION
    SELECT created_by FROM genes
    UNION
    SELECT created_by FROM transcripts
  )
SELECT
  user_id
, user_name
, api_key
, issued
FROM
  auth.users
WHERE
  user_id IN (SELECT created_by FROM all_users)
