package com.liferay.dtx;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RestController
@EnableAutoConfiguration
public class LuggageController {

	public LuggageController() {

	}

	public static void main(String[] args) {
		SpringApplication.run(LuggageController.class, args);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/luggage/{luggageId}")
	public Speech update(@PathVariable(value = "luggageId") long luggageId) {
		Luggage luggage = getLuggage(luggageId);

		return new Speech(luggage.getStatus());
	}

	private Luggage getLuggage(long luggageId) {
		List<Luggage> luggages = Arrays.asList(getLuggages());
		return luggages.get(0);
	}

	private Luggage[] getLuggages() {
		Client client = Client.create();
		WebResource webResource = client
				.resource("https://db-dtx.wedeploy.io/luggage?sort=%5B%7B%22timestamp%22:%22desc%22%7D%5D");
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		return new Gson().fromJson(response.getEntity(String.class), Luggage[].class);
	}
}