package io.swagger.sample.model;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
public class Profile  {

	private String firstName = null;
	private String lastName = null;
	private String email = null;
	private String picture = null;
	private String upi = null;

	public Profile(){
		super();
	}

	public Profile(String firstName, String upi){
		this.firstName = firstName;
		this.upi = upi;
	}

	public Profile(String firstName, String lastName, String upi, String email){
		this.firstName = firstName;
		this.lastName = lastName;
		this.upi = upi;
		this.email = email;
	}

	/**
	 * First name
	 **/
	@ApiModelProperty(value = "First name")
	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * Last name
	 **/
	@ApiModelProperty(value = "Last name")
	@JsonProperty("last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * Primary email address
	 **/
	@ApiModelProperty(value = "Primary email address")
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Image URL
	 **/
	@ApiModelProperty(value = "Image URL")
	@JsonProperty("picture")
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}


	/**
	 * upi
	 **/
	@ApiModelProperty(value = "upi")
	@JsonProperty("upi")
	public String getUpi() {
		return upi;
	}
	public void setUpi(String upi) {
		this.upi = upi;
	}



	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Profile {\n");

		sb.append("  firstName: ").append(firstName).append("\n");
		sb.append("  lastName: ").append(lastName).append("\n");
		sb.append("  email: ").append(email).append("\n");
		sb.append("  picture: ").append(picture).append("\n");
		sb.append("  upi: ").append(upi).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
