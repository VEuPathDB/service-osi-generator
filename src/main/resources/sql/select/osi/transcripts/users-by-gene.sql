SELECT DISTINCT
  created_by
FROM
  osi.transcripts
WHERE
  gene_id = ?
;
