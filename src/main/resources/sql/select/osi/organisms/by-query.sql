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
  osi.organisms AS o
  INNER JOIN auth.users AS u
    ON o.created_by = u.user_id
WHERE
  -- by name
  (
    $1 IS NULL
    OR LOWER(o.name) LIKE LOWER($1)
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
    (
      $4 IS NULL
      OR o.created_by = $4
    )
    OR (
      $5 IS NULL
      OR LOWER(u.user_name) = LOWER($5)
    )
  )
;
