SELECT
  user_id
, user_name
, api_key
, issued
FROM
  auth.users
WHERE
  user_name = ?
;
