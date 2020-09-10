-- Update an organism's template pattern
--
-- Expected inputs:
--   1. string New template string
--   2. int32  Organism ID
UPDATE
  osi.organisms
SET
  template = ?
WHERE
  organism_id = ?
;
