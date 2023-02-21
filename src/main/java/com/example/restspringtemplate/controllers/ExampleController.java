package com.example.restspringtemplate.controllers;

import com.example.restspringtemplate.net.http.HttpRequest;
import com.example.restspringtemplate.net.http.HttpResponse;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExampleController {
	@GetMapping("/example")
	public void getExample(HttpServletResponse res) throws IOException {
		new HttpResponse(res).ok().send("Hello world!");
	}

	@PostMapping("/example/post")
	public void postExample(HttpRequest request, HttpServletResponse res) throws IOException {
		HttpResponse response = new HttpResponse(res);

		JsonObject body = request.body();
		String name = body.get("name").getAsString();

		response.ok().send(name);
	}
}
