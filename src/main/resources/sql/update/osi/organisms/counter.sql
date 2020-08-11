UPDATE
  osi.organisms AS cur
SET
  gene_counter_current = gene_counter_current + ?
, modified = now()
FROM
  osi.organisms AS old
WHERE
  cur.organism_id = old.organism_id
  AND cur.organism_id = ?
RETURNING
  old.gene_counter_current AS old_counter
;
