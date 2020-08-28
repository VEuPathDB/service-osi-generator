package org.veupathdb.service.osi.util;

public final class Field
{
  static final class Common
  {
    static final String
      CREATED_BY = "createdBy",
      CREATED_ON = "createdOn";
  }

  public static final class Collection
  {
    public static final String
      NAME = "name";
  }

  public static final class IdSet
  {
    public static final String
      COLLECTION_ID   = "collectionId",
      CREATED_BY      = Common.CREATED_BY,
      CREATED_ON      = Common.CREATED_ON,
      GENE_INT_START  = "geneIntStart",
      GENERATE_GENES  = "generateGenes",
      GENERATED_GENES = "generatedGeneCount",
      ID              = "idSetId",
      ORGANISM_ID     = "organismId",
      TEMPLATE        = "template";
  }

  public static final class Organism
  {
    public static final String
      ID             = "organismId",
      NAME           = "organismName",
      TEMPLATE       = "template",
      GENE_INT_START = "geneIntStart",
      TRAN_INT_START = "transcriptIntStart",
      GENE_INT_CURR  = "geneIntCurrent",
      TRAN_INT_CURR  = "transcriptIntCurrent",
      CREATED_BY     = Common.CREATED_BY,
      CREATED_ON     = Common.CREATED_ON;
  }

  public static final class User
  {
    public static final String
      USERNAME = "username",
      API_KEY  = "apiKey",
      ID       = "userId",
      ISSUED   = "issued";
  }
}
