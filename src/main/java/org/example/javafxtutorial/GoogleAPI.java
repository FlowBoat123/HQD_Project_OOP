package org.example.javafxtutorial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class GoogleAPI {
    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String MY_API_KEY = "AIzaSyAxz15rEaspqhv_sPHwlLqSAsv4w1z0lUo";
    private static String generateBookSearchURLbyName(String query) {
        return GOOGLE_BOOKS_URL + query;
    }

    private static String generateBookSearchURLbyISBN(String ISBN) {
        return GOOGLE_BOOKS_URL + "isbn:" + ISBN;
    }
    public static ArrayList<Book> searchBook(String searchQuery) throws Exception {
        URI url;
        if (searchQuery == null || searchQuery.isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be null or empty");
        } else if (ISBNValidator.isValidISBN(searchQuery)) {
            url = new URI(generateBookSearchURLbyISBN(searchQuery));
        } else {
            url = new URI(generateBookSearchURLbyName(searchQuery));
        }
        HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("HTTP error: " + connection.getResponseCode());
        }
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        Gson gson = new Gson();
        JsonObject responseJson = gson.fromJson(reader, JsonObject.class);
        reader.close();

        if (!responseJson.has("items")){
            return null;
        }
        ArrayList<Book> searchResult = new ArrayList<>();
        JsonArray items = responseJson.getAsJsonArray("items");
        for (JsonElement item : items) {
            JsonObject itemJson = item.getAsJsonObject().getAsJsonObject("volumeInfo");
            String isbn10 = null;
            String isbn13 = null;
            JsonArray identifiers = itemJson.getAsJsonArray("industryIdentifiers");
            for (JsonElement identifier : identifiers) {
                JsonObject identifierJson = identifier.getAsJsonObject();
                if (identifierJson.get("type").getAsString().equals("ISBN_10")) {
                    isbn10 = identifierJson.get("identifier").getAsString();
                } else if (identifierJson.get("type").getAsString().equals("ISBN_13")) {
                    isbn13 = identifierJson.get("identifier").getAsString();
                }
            }
            String coverImageUrl = null;
            if (itemJson.has("imageLinks")) {
                JsonObject imageLinks = itemJson.getAsJsonObject("imageLinks");
                coverImageUrl = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : null;
            }
            Book book = new Book();
            book.setTitle(itemJson.get("title").getAsString());
            book.setAuthor(itemJson.getAsJsonArray("authors").get(0).getAsString());
            book.setPublisher(itemJson.has("publisher") ? itemJson.get("publisher").getAsString() : null);
            book.setDescription(itemJson.has("description") ? itemJson.get("description").getAsString() : null);
            book.setIsbn_10(isbn10);
            book.setIsbn_13(isbn13);
            book.setCoverImgUrl(coverImageUrl);
            searchResult.add(book);
        }
        return searchResult;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(searchBook("harrypotterand"));
    }
}
