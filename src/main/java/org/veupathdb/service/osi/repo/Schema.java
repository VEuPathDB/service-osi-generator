package org.veupathdb.service.osi.repo;

interface Schema
{
  String
    AUTH = "auth",
    OSI  = "osi";

  interface Auth
  {
    String TABLE_USERS = Schema.AUTH + ".users";

    interface Users
    {
      String
        USER_ID = "user_id",
        COLUMN_USER_EMAIL = "user_email",
        COLUMN_API_KEY    = "api_key",
        COLUMN_ISSUED     = "issued";
    }
  }

  interface Osi
  {
    String
      TABLE_ORGANISMS          = Schema.OSI + ".organisms",
      TABLE_ID_SET_COLLECTIONS = Schema.OSI + ".id_set_collections",
      TABLE_ID_ID_SETS         = Schema.OSI + ".id_sets",
      TABLE_GENES              = Schema.OSI + ".genes",
      TABLE_TRANSCRIPTS        = Schema.OSI + ".transcripts";

    interface Organisms
    {
      String
        ORGANISM_ID                = "organism_id",
        TEMPLATE                   = "template",
        GENE_COUNTER_START         = "gene_counter_start",
        GENE_COUNTER_CURRENT       = "gene_counter_current",
        TRANSCRIPT_COUNTER_START   = "transcript_counter_start",
        TRANSCRIPT_COUNTER_CURRENT = "transcript_counter_current",
        CREATED_BY                 = "created_by",
        CREATED_ON                 = "created",
        LAST_MODIFIED              = "modified";
    }
  }
}
