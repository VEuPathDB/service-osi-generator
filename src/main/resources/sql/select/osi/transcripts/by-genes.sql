SELECT
  gene_id                    AS t_gene_id
, counter_start              AS t_counter_start
, num_issued                 AS t_num_issued
, created                    AS t_created
, created_by                 AS t_created_by
FROM
  osi.transcripts
WHERE
  gene_id IN (unnest(?::INT[]))
;
