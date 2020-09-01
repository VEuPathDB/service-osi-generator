INSERT INTO
  auth.users (user_name, api_key)
VALUES
  (?, ?)
RETURNING
  user_id
, issued
;
