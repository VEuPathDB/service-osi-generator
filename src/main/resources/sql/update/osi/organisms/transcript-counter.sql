-- Atomic counter update returning the pre-update counter value.
UPDATE
  osi.organisms AS cur
SET
  transcript_counter_current = cur.transcript_counter_current + ?
, modified = now()
FROM
  osi.organisms AS old
WHERE
  cur.organism_id = old.organism_id
  AND cur.organism_id = ?
RETURNING
  old.transcript_counter_current
;
