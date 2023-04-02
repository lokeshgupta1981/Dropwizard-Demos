package com.howtodoinjava.app;

import com.howtodoinjava.app.config.ApplicationHealthCheck;
import com.howtodoinjava.app.repository.EmployeeRepository;
import com.howtodoinjava.app.web.EmployeeController;
import io.dropwizard.core.Application;
import io.dropwizard.core.Configuration;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<Configuration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  @Override
  public void initialize(Bootstrap<Configuration> b) {
  }

  @Override
  public void run(Configuration c, Environment e) throws Exception {
    LOGGER.info("Registering REST resources");
    e.jersey().register(new EmployeeController(e.getValidator(), new EmployeeRepository()));

    LOGGER.info("Registering Application Health Check");
    e.healthChecks().register("application", new ApplicationHealthCheck());
  }

  public static void main(String[] args) throws Exception {
    new App().run(args);
  }
}
