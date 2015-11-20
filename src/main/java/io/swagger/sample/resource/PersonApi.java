package io.swagger.sample.resource;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

import io.swagger.sample.data.PersonData;
import io.swagger.sample.exception.NotFoundException;
import io.swagger.sample.model.Profile;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.security.Principal;


@Path("/person")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Api(value = "/person", description = "the me API"/*, authorizations = {
		@Authorization(value = "pp_oauth",
				scopes = {
						@AuthorizationScope(scope = "person:write", description = "modify person"),
						@AuthorizationScope(scope = "person:read", description = "perosn info")
				})
}*/)
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2015-10-28T21:24:27.374Z")
public class PersonApi {

	Request request = null;

	@Context
	public void setRequest(Request request) {
		// injection into a setter method
		this.request = request;
	}

	// all the mockup services
	@GET
	@Path("/setMeToBob")
	@ApiOperation(value = "A quick way to sets value of me to Bob profile",
			notes = "For testing purposes",
			response = String.class,  produces = MediaType.TEXT_PLAIN)
	public Response setMeToBob() throws NotFoundException {
		if (PersonData.get(PersonData.BOB.getUpi())==null)
			PersonData.replace(PersonData.BOB);

		return Response.ok().entity(PersonData.setMe(PersonData.BOB.getUpi())).build();
	}

	@GET
	@Path("/setOperatorToBob")
	@ApiOperation(value = "A quick way to sets value of operator to Bob profile",
			notes = "For testing purposes",
			response = String.class,  produces = MediaType.TEXT_PLAIN)
	public Response setOperatorToBob() throws NotFoundException {
		if (PersonData.get(PersonData.BOB.getUpi())==null)
			PersonData.replace(PersonData.BOB);

		return Response.ok().entity(PersonData.setOperator(PersonData.BOB.getUpi())).build();
	}

	@GET
	@Path("/setOperatorToKen")
	@ApiOperation(value = "A quick way to sets value of operator to Ken profile",
			notes = "For testing purposes",
			response = String.class,  produces = MediaType.TEXT_PLAIN)
	public Response setOperatorToKen() throws NotFoundException {
		if (PersonData.get(PersonData.KEN.getUpi())==null)
			PersonData.replace(PersonData.KEN);

		return Response.ok().entity(PersonData.setOperator(PersonData.KEN.getUpi())).build();
	}

	@GET
	@Path("/me")
	@ApiOperation(value = "Current User Profile",
			notes = "The User Profile endpoint returns information about the user that has authorized with the application.",
			response = Profile.class, authorizations = {
			@Authorization(value = "pp_oauth", scopes = {
					@AuthorizationScope(scope = "person:read", description = "access to any person's info")
			})
	})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Profile information for a user"),
			@ApiResponse(code = 400, message = "Unexpected error") })
	public Response getMe() throws NotFoundException {
		return Response.ok().entity(PersonData.getMe()).build();
	}

	@GET
	@Path("/operator")
	@ApiOperation(value = "Current operator of person related processes",
			notes = "To be consumed by BPM flows using Basic auth",
			response = Profile.class, authorizations = @Authorization(value = "pp_basic")
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Сurent operator (Patrick if none)"),
			@ApiResponse(code = 400, message = "Unexpected error") })
	public Response getOperator(@Context SecurityContext sc,
								@ApiParam(value="Authentication") @HeaderParam("Authorization") String auth) throws NotFoundException {
		Principal pp = sc.getUserPrincipal();
		return Response.ok().entity(PersonData.getOperator()).build();
	}

	@PUT
	@Path("/operator/{newOperatorUpi}")
	@ApiOperation(value = "Current operator of person related processes",
			notes = "To be consumed by BPM flows using Basic auth",
			response = String.class, produces = MediaType.TEXT_PLAIN
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Previous operator (upi)"),
			@ApiResponse(code = 400, message = "Unexpected error") })
	public Response setOperator(@ApiParam(value="upi of new operator") @PathParam("newOperatorUpi") String newUpi){
		return Response.ok().entity(PersonData.setOperator(newUpi)).build();
	}


	@PUT
	@Path("/person/me/{myUpi}")
	@ApiOperation(value = "Currently logged person. This is what is returned me call to /me",
			notes = "This is a mockup",
			response = String.class, produces = MediaType.TEXT_PLAIN
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Previous upi"),
			@ApiResponse(code = 400, message = "Unexpected error") })
	public Response setMe(@ApiParam(value="my upi") @PathParam("myUpi") String newUpi){
		return Response.ok().entity(PersonData.setMe(newUpi)).build();
	}

	@GET
	@Path("/person/login")
	@ApiOperation(value="Sets a currently logged in person from the token",
			response = String.class, produces = MediaType.TEXT_PLAIN
	)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid upi value"),
			@ApiResponse(code = 401, message = "Absent or incorect authentication information"),
			@ApiResponse(code = 404, message = "Person not found")
	})
	public Response login(@ApiParam(value = "toke received from AS") @QueryParam("token") String token){
		// TODO verify token
		// TODO extract user information from the token
		return Response.status(Response.Status.FORBIDDEN).build();
	}


	@GET
	@Path("/{upi}")
	@ApiOperation(value = "Get person by upi",
			notes = "Use pck123 as upi. Use 20 as api_key (or 123 to test FORBIDDEN with message)",
			response = Profile.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid upi value"),
			@ApiResponse(code = 401, message = "Invalid api_key"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Person not found")
	})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUpi(
			@HeaderParam("api_key") String api_key,
			@ApiParam(value = "upi of a person", required = true, allowMultiple = true) @PathParam("upi") String upi) throws NotFoundException {

		if (api_key==null || api_key.charAt(api_key.length()-1) != '0'){
				return Response.status(Response.Status.FORBIDDEN).build();// .entity("Invalid api key '"+api_key+"'").build();
		}else if (api_key.equals("123")){
			return Response.status(Response.Status.FORBIDDEN).entity("Invalid api key '"+api_key+"'").build();
		}

		// return Response.status(Status.OK).entity(dto).type(request.getContentType()).build();
		Profile result = PersonData.get(upi);
		if (result!=null)
			return Response.ok().entity(result).build();

		if (upi!=null && upi.equals("pck123"))
			return Response.ok().entity(new Profile("Patrick", "pck123")).build();
		else
			//throw new NotFoundException(400, "Invalid upi key");
			return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@ApiOperation(value = "Add person (upi is required)",
			notes = "This can only be done by the logged in user.",
			position = 1, authorizations = {
			@Authorization(value = "pp_oauth", scopes = {
					@AuthorizationScope(scope = "person:write", description = "access to any person's info")
			})})
	public Response createUser(
			@ApiParam(value = "Person with upi", required = true) Profile person) {
		PersonData.replace(person);
		return Response.ok().entity("").build();
	}
}