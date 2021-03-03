package test.sets;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import test.*;
import test.organisms.OrganismUtil;

import static io.restassured.RestAssured.with;

public class IdSetTestBase extends AuthTestBase
{
  public static final String FLAT_PATH = "/idSets";
  public static final String ID_PATH   = FLAT_PATH + "/{id}";

  protected static final String FLAT_URL = makeUrl(FLAT_PATH);
  protected static final String ID_URL   = makeUrl(ID_PATH);

  protected long organismId;
  protected String template;

  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();

    template     = TestUtil.randStr().substring(0, 28) + "%d";
    organismId   = OrganismUtil.createOrganism("organism", template, user.getUserId());
  }

  protected IdSetResponse getIdSet(final long id) {
    return with().header("Authorization", authHeader())
      .get(ID_URL, id).as(IdSetResponse.class);
  }

  protected IdSetResponse createIdSet(final IdSetPostRequest req) {
    return with()
      .contentType(ContentType.JSON)
      .header("Authorization", authHeader())
      .body(req)
      .post(FLAT_URL)
      .as(IdSetResponse.class);
  }

  protected IdSetResponse createIdSet(final int genCount) {
    return createIdSet(new IdSetPostRequest().
        setOrganismId(organismId).
        setGenerateGenes(genCount));
  }

  protected int geneIdComparator(
    final GeneratedTranscriptEntry a,
    final GeneratedTranscriptEntry b
  ) {
    var geneIdA = (Integer) Integer.parseInt(a.getGeneId().substring(28));
    var geneIdB = (Integer) Integer.parseInt(b.getGeneId().substring(28));

    return geneIdA.compareTo(geneIdB);
  }
}
