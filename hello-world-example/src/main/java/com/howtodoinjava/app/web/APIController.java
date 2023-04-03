package com.howtodoinjava.app.web;

import com.howtodoinjava.app.model.Employee;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("client-root-path")
public class APIController {

  private Client jerseyClient;

  public APIController(Client jerseyClient) {
    this.jerseyClient = jerseyClient;
  }

  @GET
  @Path("/employees/")
  public String getEmployees() {
    //Do not hard code the path in your application
    WebTarget webTarget = jerseyClient.target("http://localhost:8080/employees");
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    Response response = invocationBuilder.get();
    ArrayList<Employee> employees = response.readEntity(ArrayList.class);
    return employees.toString();
  }

  @GET
  @Path("/employees/{id}")
  public String getEmployeeById(@PathParam("id") int id) {
    //Do not hard code the path in your application
    WebTarget webTarget = jerseyClient.target("http://localhost:8080/employees/" + id);
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    Response response = invocationBuilder.get();
    Employee employee = response.readEntity(Employee.class);
    return employee.toString();
  }
}
