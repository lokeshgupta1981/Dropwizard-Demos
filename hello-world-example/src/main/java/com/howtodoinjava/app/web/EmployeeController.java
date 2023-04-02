package com.howtodoinjava.app.web;

import com.howtodoinjava.app.model.Employee;
import com.howtodoinjava.app.repository.EmployeeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeController {

  private Validator validator;
  private EmployeeRepository repository;

  public EmployeeController(Validator validator, EmployeeRepository repository) {
    this.validator = validator;
    this.repository = repository;
  }

  @GET
  public Response getEmployees() {
    return Response.ok(repository.getEmployees()).build();
  }

  @GET
  @Path("/{id}")
  public Response getEmployeeById(@PathParam("id") Integer id) {
    Employee employee = repository.getEmployee(id);
    if (employee != null) {
      return Response.ok(employee).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @POST
  public Response createEmployee(Employee employee) throws URISyntaxException {
    // validation
    Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
    Employee e = repository.getEmployee(employee.getId());
    if (violations.size() > 0) {
      ArrayList<String> validationMessages = new ArrayList<String>();
      for (ConstraintViolation<Employee> violation : violations) {
        validationMessages.add(
            violation.getPropertyPath().toString() + ": " + violation.getMessage());
      }
      return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
    }
    if (e != null) {
      repository.updateEmployee(employee.getId(), employee);
      return Response.created(new URI("/employees/" + employee.getId()))
          .build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @PUT
  @Path("/{id}")
  public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {
    // validation
    Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
    Employee e = repository.getEmployee(employee.getId());
    if (violations.size() > 0) {
      ArrayList<String> validationMessages = new ArrayList<String>();
      for (ConstraintViolation<Employee> violation : violations) {
        validationMessages.add(
            violation.getPropertyPath().toString() + ": " + violation.getMessage());
      }
      return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
    }
    if (e != null) {
      employee.setId(id);
      repository.updateEmployee(id, employee);
      return Response.ok(employee).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @DELETE
  @Path("/{id}")
  public Response removeEmployeeById(@PathParam("id") Integer id) {
    Employee employee = repository.getEmployee(id);
    if (employee != null) {
      repository.removeEmployee(id);
      return Response.ok().build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
}
