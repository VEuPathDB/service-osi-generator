SELECT
  gene_id
, id_set_id
, gene_identifier
, created
, created_by
, user_id
, user_name
, api_key
, issued
FROM
  osi.genes             AS g
  INNER JOIN auth.users AS u
  ON g.created_by = u.user_id
WHERE
  id_set_id IN (unnest(?::INT[]))
;
