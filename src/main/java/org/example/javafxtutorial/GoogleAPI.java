package org.example.javafxtutorial;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import logic.Book;

public class GoogleAPI {
    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String MY_API_KEY = "AIzaSyAxz15rEaspqhv_sPHwlLqSAsv4w1z0lUo";
    private static final String BY_ISBN = "isbn";
    private static final String BY_TITLE = "title";
    private static final String BY_AUTHOR = "author";

    private static String generateBookSearchURLbyTitle(String query) {
        return GOOGLE_BOOKS_URL + query;
    }

    private static String generateBookSearchURLbyISBN(String ISBN) {
        return GOOGLE_BOOKS_URL + "isbn:" + ISBN;
    }
    private static String generateBookSearchURLbyAuthor(String author) {
        return GOOGLE_BOOKS_URL + "inauthor:" + author;
    }

    private static JsonObject getHttpMethod(String searchQuery, String searchMethod) throws Exception {
        searchQuery = searchQuery.replaceAll("\\s", "+");
        System.out.println(searchQuery);
        URI url = getUri(searchQuery, searchMethod);
        System.out.println(url);
        HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("HTTP error: " + connection.getResponseCode());
        }
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        Gson gson = new Gson();
        JsonObject responseJson = gson.fromJson(reader, JsonObject.class);
        reader.close();
        return responseJson;
    }

    private static URI getUri(String searchQuery, String searchMethod) throws URISyntaxException {
        URI url;
        if (searchQuery.isEmpty()) {
            throw new IllegalArgumentException("Search query cannot empty");
        }
        if (searchMethod.isEmpty()) {
            throw new IllegalArgumentException("Search method invalid");
        }
        if (searchMethod.equals(BY_ISBN)) {
            url = new URI(generateBookSearchURLbyISBN(searchQuery));
        } else if (searchMethod.equals(BY_AUTHOR)) {
            url = new URI(generateBookSearchURLbyAuthor(searchQuery));
        } else {
            url = new URI(generateBookSearchURLbyTitle(searchQuery));
        }
        return url;
    }

    public static ArrayList<Book> searchBook(String searchQuery, String searchMethod) throws Exception {
        JsonObject responseJson = getHttpMethod(searchQuery, searchMethod);
        if (!responseJson.has("items")){
            return null;
        }
        ArrayList<Book> searchResult = new ArrayList<>();
        JsonArray items = responseJson.getAsJsonArray("items");
        for (JsonElement item : items) {
            JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
            if (volumeInfo.has("imageLinks") && volumeInfo.has("industryIdentifiers")) {
                searchResult.add(parseBookFromJSON(item));
            }
        }
        return searchResult;
    }
    private static Book parseBookFromJSON(JsonElement bookElement){
        JsonObject itemJson = bookElement.getAsJsonObject().getAsJsonObject("volumeInfo");
        String isbn10 = null;
        String isbn13 = null;
        JsonArray identifiers = itemJson.getAsJsonArray("industryIdentifiers");
        if (identifiers != null) {
            for (JsonElement identifier : identifiers) {
                JsonObject identifierJson = identifier.getAsJsonObject();
                if (identifierJson.get("type").getAsString().equals("ISBN_10")) {
                    isbn10 = identifierJson.get("identifier").getAsString();
                } else if (identifierJson.get("type").getAsString().equals("ISBN_13")) {
                    isbn13 = identifierJson.get("identifier").getAsString();
                }
            }
        }
        String coverImageUrl = null;
        if (itemJson.has("imageLinks")) {
            JsonObject imageLinks = itemJson.getAsJsonObject("imageLinks");
            coverImageUrl = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : null;
        }
        Book book = new Book();
        book.setTitle(itemJson.get("title").getAsString());
        JsonArray authorsJson = itemJson.getAsJsonArray("authors");
        if (authorsJson != null) {
            for (JsonElement author : authorsJson) {
                book.addAuthor(author.getAsString());
            }
        }
        JsonArray genresJson = itemJson.getAsJsonArray("categories");
        if (genresJson != null) {
            for (JsonElement genre : genresJson) {
                book.addGenre(genre.getAsString());
            }
        }
        book.setDescription(itemJson.has("description") ? itemJson.get("description").getAsString() : null);
        book.setIsbn_10(isbn10);
        book.setIsbn_13(isbn13);
        book.setCoverImgUrl(coverImageUrl);
        return book;
    }
}
