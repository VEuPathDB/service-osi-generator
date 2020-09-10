-- Find IdSetCollections by query params
--
-- Expected inputs:
--   1. string    Name (with wildcard support)
--   2. timestamp Created on or after
--   3. timestamp Created on or before
--   4. int64     Created by user id
--   5. string    Created by user name
WITH query AS (
  SELECT
    LOWER(?::VARCHAR) AS name
  , ?::TIMESTAMPTZ    AS after
  , ?::TIMESTAMPTZ    AS before
  , ?::BIGINT         AS user_id
  , LOWER(?::VARCHAR) AS user_name
)
SELECT
  c.id_set_coll_id
, c.name
, c.created_by
, c.created
FROM
  osi.id_set_collections AS c
  INNER JOIN auth.users  AS u
    ON c.created_by = u.user_id
  INNER JOIN query AS q
    ON
      -- by name
      (q.name IS NULL OR LOWER(c.name) LIKE q.name)
      -- by creation date begin
      AND (q.after IS NULL OR c.created >= q.after)
      -- by creation date end
      AND (q.before IS NULL OR c.created <= q.before)
      -- by creation user
      AND (q.user_id IS NULL OR c.created_by = q.user_id)
      AND (q.user_name IS NULL OR LOWER(u.user_name) = q.user_name)
;
