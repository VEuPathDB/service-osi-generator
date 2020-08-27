package org.veupathdb.service.osi;

import java.io.IOException;

import org.veupathdb.lib.container.jaxrs.config.Options;
import org.veupathdb.lib.container.jaxrs.server.ContainerResources;
import org.veupathdb.lib.container.jaxrs.server.Server;
import org.veupathdb.service.osi.model.config.CliConfig;
import org.veupathdb.service.osi.service.DbMan;

public class Main extends Server {
  public static void main(String[] args) throws IOException {
    var server = new Main();
    server.enableAccountDB();
    server.start(args);
  }

  @Override
  protected ContainerResources newResourceConfig(Options options) {
    final var out =  new Resources(options);

    // Enabled by default for debugging purposes, this should be removed when
    // production ready.
    out.property("jersey.config.server.tracing.type", "ALL")
      .property("jersey.config.server.tracing.threshold", "VERBOSE");

    return out;
  }

  @Override
  protected void onShutdown() {
    DbMan.getInstance().close();
  }

  @Override
  protected Options newOptions() {
    return new CliConfig();
  }

  @Override
  protected void postCliParse(Options opts) {
    DbMan.initialize((CliConfig) opts);
  }
}
