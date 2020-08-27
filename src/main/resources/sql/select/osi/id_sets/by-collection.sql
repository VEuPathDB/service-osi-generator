SELECT
  s.id_set_id
, s.id_set_coll_id
, s.organism_id
, s.template
, s.created
, s.created_by
FROM
  osi.id_sets AS s
  INNER JOIN auth.users AS u
    ON s.created_by = u.user_id
WHERE
  id_set_coll_id = ?
;
