SELECT
  user_id
, user_name
, api_key
, issued
FROM
  auth.users
WHERE
  LOWER(user_name) = LOWER(?)
;
