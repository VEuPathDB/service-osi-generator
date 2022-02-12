
package org.veupathdb.service.osi.generated.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

public class NewUserResponseStream extends NewUserResponseImpl implements StreamingOutput {

  private final Consumer<OutputStream> _streamer;

  public NewUserResponseStream(Consumer<OutputStream> streamer) {
    _streamer = streamer;
  }

  @Override
  public void write(OutputStream output) throws IOException, WebApplicationException {
    _streamer.accept(output);
  }

}

