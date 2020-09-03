SELECT
  transcript_id
, gene_id
, counter_start
, num_issued
, created
, created_by
FROM
  osi.transcripts
WHERE
  gene_id IN (SELECT unnest(?::BIGINT[])::BIGINT)
;
