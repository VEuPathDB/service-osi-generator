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
        USER_ID           = "user_id",
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

    interface Genes
    {
      String
        GENE_ID    = "gene_id",
        ID_SET_ID  = IdSets.ID_SET_ID,
        GENE_NAME  = "gene_identifier",
        CREATED_ON = "created",
        CREATED_BY = "created_by";
    }

    interface IdSetCollections
    {
      String
        COLLECTION_ID = "id_set_coll_id",
        NAME          = "name",
        CREATED_BY    = "created_by",
        CREATED_ON    = "created";
    }

    interface IdSets
    {
      String
        ID_SET_ID     = "id_set_id",
        COLLECTION_ID = IdSetCollections.COLLECTION_ID,
        ORGANISM_ID   = Organisms.ORGANISM_ID,
        TEMPLATE      = "template",
        CREATED_BY    = "created_by",
        CREATED_ON    = "created_on";
    }

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

    interface Transcripts
    {
      String
        GENE_ID = Genes.GENE_ID,
        COUNTER_START = "counter_start",
        NUM_ISSUED = "num_issued",
        CREATED_ON = "created",
        CREATED_BY = "created_by";
    }
  }

  interface JoinPrefixes
  {
    String
      TRANSCRIPTS       = "t_",
      GENES             = "g_",
      ORGANISMS         = "o_",
      ID_SET            = "s_",
      ID_SET_COLLECTION = "c_",

    GENE_USER                = "gu_",
      ORGANISM_USER          = "ou_",
      TRANSCRIPT_USER        = "tu_",
      ID_SET_USER            = "su_",
      ID_SET_COLLECTION_USER = "cu_";
  }
}
