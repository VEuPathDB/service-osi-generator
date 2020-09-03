SELECT
  gene_id
, id_set_id
, gene_identifier
, created
, created_by
FROM
  osi.genes
WHERE
  gene_identifier IN (SELECT unnest(?::VARCHAR[])::VARCHAR)
;
