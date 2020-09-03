-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--                                                                            --
-- Create a new Gene record                                                   --
--                                                                            --
-- Expected inputs:                                                           --
--   1. <int64>  Parent ID set primary key                                    --
--   2. <string> Gene identifier string                                       --
--   3. <int64>  ID of the user requesting this gene insert                   --
--                                                                            --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

INSERT INTO
  osi.genes (id_set_id, gene_identifier, created_by)
VALUES
  (?, ?, ?)
RETURNING
  gene_id
, created
;
