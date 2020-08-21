SELECT
  t.gene_id                    AS t_gene_id
, t.counter_start              AS t_counter_start
, t.num_issued                 AS t_num_issued
, t.created                    AS t_created
, t.created_by                 AS t_created_by
, g.gene_id                    AS g_gene_id
, g.id_set_id                  AS g_id_set_id
, g.gene_identifier            AS g_gene_identifier
, g.created                    AS g_created
, g.created_by                 AS g_created_by
, s.id_set_id                  AS s_id_set_id
, s.id_set_coll_id             AS s_id_set_coll_id
, s.organism_id                AS s_organism_id
, s.template                   AS s_template
, s.created                    AS s_created
, s.created_by                 AS s_created_by
, c.id_set_coll_id             AS c_id_set_coll_id
, c.name                       AS c_name
, c.created_by                 AS c_created_by
, c.created                    AS c_created
, o.organism_id                AS o_organism_id
, o.template                   AS o_template
, o.gene_counter_start         AS o_gene_counter_start
, o.gene_counter_current       AS o_gene_counter_current
, o.transcript_counter_start   AS o_transcript_counter_start
, o.transcript_counter_current AS o_transcript_counter_current
, o.created_by                 AS o_created_by
, o.created                    AS o_created
, o.modified                   AS o_modified
FROM
  osi.transcripts                   AS t
  INNER JOIN osi.genes              AS g
    USING (gene_id)
  INNER JOIN osi.id_sets            AS s
    USING (id_set_id)
  INNER JOIN osi.id_set_collections AS c
    USING (id_set_coll_id)
  INNER JOIN osi.organisms          AS o
    USING (organism_id)
WHERE
  t.gene_id = ?
;
