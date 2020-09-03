-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--                                                                            --
-- Create a new ID set record                                                 --
--                                                                            --
-- Expected inputs:                                                           --
--   1. <int64>  Parent Collection ID                                         --
--   2. <int64>  Parent Organism ID                                           --
--   3. <string> Gene ID Template String                                      --
--   4. <int64>  Gene ID counter starting point as of the creation of this    --
--               id set                                                       --
--   5. <int32>  Number of new genes issued for this ID set                   --
--   6. <int64>  ID of the user requesting this ID set insert                 --
--                                                                            --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

INSERT INTO
  osi.id_sets (
    id_set_coll_id
  , organism_id
  , template
  , counter_start
  , num_issued
  , created_by
  )
VALUES
  (?, ?, ?, ?, ?, ?)
RETURNING
  id_set_id
, created
;
