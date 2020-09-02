SELECT
  o.organism_id
, o.name
, o.template
, o.gene_counter_start
, o.gene_counter_current
, o.transcript_counter_start
, o.transcript_counter_current
, o.created
, o.modified
, o.created_by
FROM
  osi.organisms AS o
WHERE
  o.organism_id = ?
;
