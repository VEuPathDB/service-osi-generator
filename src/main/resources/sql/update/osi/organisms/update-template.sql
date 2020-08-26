-- Update an organism's template pattern
--
-- Expected inputs:
--   1. int32  Organism ID
--   2. string New template string
UPDATE
  osi.organisms
SET
  template = $2
WHERE
  organism_id = $1
;
