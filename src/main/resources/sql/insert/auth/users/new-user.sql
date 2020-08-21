INSERT INTO
  auth.users (user_email, api_key)
VALUES
  (?, ?)
RETURNING
  user_id
, issued
;
