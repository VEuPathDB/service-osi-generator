INSERT INTO
  osi.transcripts (gene_id, counter_start, num_issued, created_by)
VALUES
  (?, ?, ?, ?)
RETURNING
  transcript_id
, created
;
