-- Update an organism record.
--
-- Expected inputs:
--   1. int32  Organism ID
--   2. string New template string
--   3. int64  New gene counter starting value
--   4. int64  New transcript counter starting value
UPDATE
  osi.organisms
SET
  template = $2
, gene_counter_start = $3
, gene_counter_current = $3
, transcript_counter_start = $4
, transcript_counter_current = $4
, modified = now()
WHERE
  organism_id = $1
;
