-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--                                                                            --
-- Create a new ID set record                                                 --
--                                                                            --
-- Expected inputs:                                                           --
--   1. <int64>  Parent Organism ID                                           --
--   2. <string> Gene ID Template String                                      --
--   3. <int64>  Gene ID counter starting point as of the creation of this    --
--               id set                                                       --
--   4. <int32>  Number of new genes issued for this ID set                   --
--   5. <int64>  ID of the user requesting this ID set insert                 --
--                                                                            --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

INSERT INTO
  osi.id_sets (
    organism_id
  , template
  , counter_start
  , num_issued
  , created_by
  )
VALUES
  (?, ?, ?, ?, ?)
RETURNING
  id_set_id
, created
;
