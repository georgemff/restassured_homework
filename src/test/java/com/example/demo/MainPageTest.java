package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;


public class MainPageTest {

    RequestSpecification httpRequest;
    String lastBookIsbn;



    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/";
        httpRequest = RestAssured.given();
    }

    @Test
    public void getBooks() {
        Response response = httpRequest.get("Books");
        BooksResponse responseBody = response.getBody().as(BooksResponse.class);
        Assert.assertEquals(200, response.statusCode());

        lastBookIsbn = responseBody.books[responseBody.books.length - 1].isbn;
    }

    @Test
    public void getIsbn() {
        Response isbnResponse = httpRequest.queryParam("ISBN", lastBookIsbn).get("Book");

        BookResponse bookResponse = isbnResponse.getBody().as(BookResponse.class);
        Assert.assertEquals("Understanding ECMAScript 6", bookResponse.title);
        Assert.assertEquals("The Definitive Guide for JavaScript Developers", bookResponse.subTitle);

    }
}
