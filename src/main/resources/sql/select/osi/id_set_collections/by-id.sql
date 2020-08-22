SELECT
  c.id_set_coll_id
, c.name
, c.created_by
, c.created
, u.user_id
, u.user_name
, u.api_key
, u.issued
FROM
  osi.id_set_collections AS c
  INNER JOIN auth.users  AS u
    ON c.created_by = u.user_id
WHERE
  id_set_coll_id = ?
;
