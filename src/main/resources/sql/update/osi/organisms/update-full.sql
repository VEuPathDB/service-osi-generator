-- Update an organism record.
--
-- Expected inputs:
--   1. int32  Organism ID
--   2. string New template string
--   3. int64  New gene counter starting value
--   4. int64  New transcript counter starting value
WITH
  row_id     AS (SELECT ?::BIGINT AS val)
, template   AS (SELECT ?::VARCHAR AS val)
, gene_start AS (SELECT ?::BIGINT AS val)
, tran_start AS (SELECT ?::BIGINT AS val)
UPDATE
  osi.organisms
SET
  template                   = (SELECT val FROM template)
, gene_counter_start         = (SELECT val FROM gene_start)
, gene_counter_current       = (SELECT val FROM gene_start)
, transcript_counter_start   = (SELECT val FROM tran_start)
, transcript_counter_current = (SELECT val FROM tran_start)
, modified                   = now()
WHERE
  organism_id = (SELECT val FROM row_id)
;
