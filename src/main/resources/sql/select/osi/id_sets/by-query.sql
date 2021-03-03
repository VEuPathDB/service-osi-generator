WITH
  query AS (
    SELECT
      ?::TIMESTAMPTZ    AS after
    , ?::TIMESTAMPTZ    AS before
    , ?::BIGINT         AS user_id
    , LOWER(?::VARCHAR) AS user_name
  )
SELECT
  s.id_set_id
, s.organism_id
, s.template
, s.counter_start
, s.num_issued
, s.created
, s.created_by
FROM
  osi.id_sets           AS s
  INNER JOIN auth.users AS u
  ON s.created_by = u.user_id
  INNER JOIN query      AS q
  ON
    -- by creation date begin
      (q.after IS NULL OR s.created >= q.after)
      -- by creation date end
      AND (q.before IS NULL OR s.created <= q.before)
      -- by creation user
      AND (q.user_id IS NULL OR s.created_by = q.user_id)
      AND (q.user_name IS NULL OR LOWER(u.user_name) = q.user_name)
;
