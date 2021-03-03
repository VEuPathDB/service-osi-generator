package org.veupathdb.service.osi;

import org.veupathdb.lib.container.jaxrs.server.ContainerResources;
import org.veupathdb.service.osi.controller.AuthController;
import org.veupathdb.service.osi.controller.IdSetController;
import org.veupathdb.service.osi.controller.OrganismController;
import org.veupathdb.service.osi.filter.BasicAuthFilter;
import org.veupathdb.service.osi.model.config.CliConfig;

/**
 * Service Resource Registration.
 *
 * This is where all the individual service specific resources and middleware
 * should be registered.
 */
public class Resources extends ContainerResources {
  public Resources(CliConfig opts) {
    super(opts);
  }

  /**
   * Returns an array of JaxRS endpoints, providers, and contexts.
   *
   * Entries in the array can be either classes or instances.
   */
  @Override
  protected Object[] resources() {
    return new Object[] {
      new BasicAuthFilter(
        Main.config.getAdminUser(),
        Main.config.getAdminPass()),
      AuthController.class,
      OrganismController.class,
      IdSetController.class
    };
  }
}
