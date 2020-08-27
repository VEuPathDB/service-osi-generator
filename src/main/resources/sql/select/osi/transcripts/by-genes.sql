SELECT
  gene_id
, counter_start
, num_issued
, created
, created_by
, user_id
, user_name
, api_key
, issued
FROM
  osi.transcripts       AS t
  INNER JOIN auth.users AS u
    ON t.created_by = u.user_id
WHERE
  gene_id IN (unnest(?::BIGINT[]))
;
