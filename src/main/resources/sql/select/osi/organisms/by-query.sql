WITH query AS (
  SELECT
    LOWER(?::VARCHAR) AS name
  , ?::TIMESTAMPTZ    AS after
  , ?::TIMESTAMPTZ    AS before
  , ?::BIGINT         AS user_id
  , LOWER(?::VARCHAR) AS user_name
)
SELECT
  o.organism_id
, o.name
, o.template
, o.gene_counter_start
, o.gene_counter_current
, o.transcript_counter_start
, o.transcript_counter_current
, o.created_by
, o.created
, o.modified
FROM
  osi.organisms AS o
  INNER JOIN auth.users AS u
    ON o.created_by = u.user_id
  INNER JOIN query AS q
    ON
      -- by name
      (q.name IS NULL OR LOWER(o.name) LIKE q.name)
      -- by creation date begin
      AND (q.after IS NULL OR o.created >= q.after)
      -- by creation date end
      AND (q.before IS NULL OR o.created <= q.before)
      -- by creation user
      AND (q.user_id IS NULL OR o.created_by = q.user_id)
      AND (q.user_name IS NULL OR LOWER(u.user_name) = q.user_name)
;
