SELECT
  o.organism_id
, o.template
, o.gene_counter_start
, o.gene_counter_current
, o.transcript_counter_start
, o.transcript_counter_current
, o.created_by
, o.created
, o.modified
, u.user_id
, u.user_email
, u.api_key
, u.issued
FROM
  osi.organisms AS o
  INNER JOIN auth.users AS u
    ON o.created_by = u.user_id
WHERE
  -- by name
  (
    $1 IS NULL
    OR o.organism_id LIKE $1
  )
  -- by creation date begin
  AND (
    $2 IS NULL
    OR o.created >= $2
  )
  -- by creation date end
  AND (
    $3 IS NULL
    OR o.created <= $3
  )
  -- by creation user
  AND (
    $4 IS NULL
    OR o.created_by = $4
  )
;
