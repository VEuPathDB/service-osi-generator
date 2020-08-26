SELECT
  id_set_coll_id
, name
, created_by
, created
, user_id
, user_name
, api_key
, issued
FROM
  osi.id_set_collections AS c
  INNER JOIN auth.users  AS u
    ON c.created_by = u.user_id
WHERE
  -- name wildcard
  (
    $1 IS NULL
    OR name LIKE $1
  )
  -- created date lower bound
  AND (
    $2 IS NULL
    OR created >= $2
  )
  -- created date upper bound
  AND (
    $3 IS NULL
    OR created <= $3
  )
  -- creation user id
  AND (
    $4 IS NULL
    OR created_by = $4
  )
  -- creation user name
  AND (
    $5 IS NULL
    OR created_by = (SELECT user_id FROM auth.users WHERE user_name = $5)
  )
;
