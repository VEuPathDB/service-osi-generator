SELECT
  g.gene_id
, g.id_set_id
, g.gene_identifier
, g.created
, g.created_by
, u.user_id
, u.user_name
, u.api_key
, u.issued
FROM
  osi.genes             AS g
  INNER JOIN auth.users AS u
    ON g.created_by = u.user_id
WHERE
  id_set_id = ?
;
