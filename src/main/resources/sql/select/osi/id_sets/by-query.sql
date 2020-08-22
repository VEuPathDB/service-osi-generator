SELECT
  id_set_id
, id_set_coll_id
, organism_id
, template
, num_issued
, created
, created_by
FROM
  osi.id_sets
WHERE
  -- creation date lower bound
  (
    $1 IS NULL
    OR created >= $1
  )
  -- creation date upper bound
  AND (
    $2 IS NULL
    OR created <= $2
  )
  -- user id
  AND (
    $3 IS NULL
    OR created_by = $3
  )
  -- user name
  AND (
    $4 IS NULL
    OR created_by = (SELECT user_id FROM auth.users WHERE user_name = $4)
    )
;
