package io.swagger.sample.resource;


import io.swagger.annotations.*;
import io.swagger.sample.data.PersonData;
import io.swagger.sample.data.SecretData;
import io.swagger.sample.exception.NotFoundException;
import io.swagger.sample.model.Profile;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;


@Path("/secrets")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Api(value = "/secrets", description = "the secrets API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2015-10-28T21:24:27.374Z")
public class SecretsApi {

	Request request = null;

	@Context
	public void setRequest(Request request) {
		// injection into a setter method
		this.request = request;
	}

	@GET
	@Path("/{secretId}")
	@ApiOperation(value = "Value of secret",
			notes = "Anyone can overwrite this value",
			response = String.class
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Value of secret"),
			@ApiResponse(code = 400, message = "Unexpected error") })
	@Produces({MediaType.TEXT_PLAIN})
	public Response getMe(@ApiParam(value="name") @PathParam("secretId") String name) throws NotFoundException {
		return Response.ok().entity(SecretData.getSecret(name)).build();
	}

	@POST
	@Path("/{secretId}")
	@ApiOperation(value = "Current operator of person related processes",
			notes = "To be consumed by BPM flows using Basic auth",
			response = String.class, produces = MediaType.TEXT_PLAIN
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Previous value"),
			@ApiResponse(code = 400, message = "Unexpected error") })
	public Response setOperator(
			@ApiParam(value="secret's id (name)") @PathParam("secretId") String name,
			@ApiParam(value = "new value") @QueryParam("value") String value){

		return Response.ok().entity(SecretData.putSecret(name, value)).build();
	}

}