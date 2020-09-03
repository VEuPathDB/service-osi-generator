WITH
  sets AS (
    SELECT
      id_set_id
    FROM
      osi.id_sets
    WHERE
      id_set_coll_id IN (SELECT unnest(?::BIGINT[]))
  )
, genes AS (
    SELECT
      gene_id
    FROM
      osi.genes
    WHERE
      id_set_id IN (SELECT id_set_id FROM sets)
  )
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
  gene_id IN (SELECT gene_id FROM genes)
;
