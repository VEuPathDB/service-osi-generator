SELECT
  user_id
, user_email
, api_key
, issued
FROM
  auth.users
WHERE
  user_email = ?
  AND api_key = ?
;
