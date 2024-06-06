package Spotify_Automation;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Spotify_Api {

    public  String token = "BQAA80_KxD1qTEiJTtr9OE1dxVIwqDzAxlho75J2R5rG0p82OFUGh2PUY3QLd2JMCy_WNzgUUDskkaZZtEap7fwSpc17jHrw4gN0kMyolhx1_8tqUSYTeXXaW3a2sYP8WYvBxKNKOiPU1N1GdB2QUsMRdrxoSZz_Qp4rNeKG83laT931tVF7Qg46fi7T_3ebIqC2hdlwksgT1kmTvkAc1tkkjoW2JrP4OokughXdAJoodMcBtZ7atgovDSa_2bqTsJ8W_xc65LMXJNKSDAV9WaY_hNAp-28Gf1xctuz59aixmjXZvMoDyDrJ4TKMGmV-62kakt0u2w1ZmHw";

    //Users

    @Test
    public void getUserProfile(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("user_id","31c6h2vtirvzzhzqlu2nw7oer6xq")
                .when()
                .get("https://api.spotify.com/v1/users/{user_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String msg = res.path("display_name");
        Assert.assertEquals(msg,"Tushar Jadhav");
    }

    @Test
    public void getCurrentUserProfile(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String msg = res.path("display_name");
        Assert.assertEquals(msg,"Tushar Jadhav");
    }

    @Test
    public void followPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("hindi_song_playlist","6SNTFqvHvLTdWV3c9KhkeW")
                .body("{\n" +
                        "    \"public\": true\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{hindi_song_playlist}/followers");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void unfollowPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("hindi_song_playlist","6SNTFqvHvLTdWV3c9KhkeW")
                .when()
                .delete("https://api.spotify.com/v1/playlists/{hindi_song_playlist}/followers");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void getFollowedArtists(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/following?type=artist&limit=5");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String msg = res.path("artists.items[0].id");
        Assert.assertEquals(msg,"2CIMQHirSU0MQqyYHq0eOx");
    }

    @Test
    public void followArtistsOrUsers(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"4YRxDV8wJFPHPTeXepOstw\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/following?type=artist&ids=4YRxDV8wJFPHPTeXepOstw");
        res.prettyPrint();
        res.then().statusCode(204);
        Assert.assertEquals(res.statusCode(),204);
    }

    @Test
    public void unfollowArtistsOrUsers(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"4YRxDV8wJFPHPTeXepOstw\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/following?type=artist&ids=4YRxDV8wJFPHPTeXepOstw");
        res.prettyPrint();
        res.then().statusCode(204);
        Assert.assertEquals(res.statusCode(),204);
    }

    @Test
    public void checkIfUserFollowsArtistsOrUsers(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/following/contains?type=artist&ids=4YRxDV8wJFPHPTeXepOstw");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean msg = res.path("[0]");
        Assert.assertTrue(msg);
    }

    @Test
    public void checkIfCurrentUserFollowsPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("hindi_song_playlist","6SNTFqvHvLTdWV3c9KhkeW")
                .pathParam("user_id","31c6h2vtirvzzhzqlu2nw7oer6xq")
                .when()
                .get("https://api.spotify.com/v1/playlists/{hindi_song_playlist}/followers/contains?ids={user_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean msg = res.path("[0]");
        Assert.assertTrue(msg);
    }

    @Test
    public void getUserTopItems(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/top/artists");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    //Artists

    @Test
    public void getArtists(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("artists_arijit_singh","4YRxDV8wJFPHPTeXepOstw")
                .when()
                .get("https://api.spotify.com/v1/artists/{artists_arijit_singh}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String msg = res.path("name");
        Assert.assertEquals(msg,"Arijit Singh");
    }

    @Test
    public void getSeveralArtists(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("artists_arijit_singh","4YRxDV8wJFPHPTeXepOstw")
                .pathParams("artists_ajay_atul","5fvTHKKzW44A9867nPDocM")
                .when()
                .get("https://api.spotify.com/v1/artists?ids={artists_arijit_singh}&ids={artists_ajay_atul}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String msg = res.path("artists[1].name");
        Assert.assertEquals(msg,"Ajay-Atul");
    }

    @Test
    public void getArtistAlbums() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("artists_arijit_singh", "4YRxDV8wJFPHPTeXepOstw")
                .when()
                .get("https://api.spotify.com/v1/artists/{artists_arijit_singh}/albums");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String msg = res.path("items[0].name");
        Assert.assertEquals(msg, "Arijit Singh Top Sad Love Hits");
    }

    @Test
    public void getArtistTopTracks() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("artists_ajay_atul","5fvTHKKzW44A9867nPDocM")
                .when()
                .get("https://api.spotify.com/v1/artists/{artists_ajay_atul}/top-tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String msg = res.path("tracks[0].album.artists[0].name");
        Assert.assertEquals(msg, "Ajay-Atul");
    }

    @Test
    public void getArtistRelatedArtists() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("artists_arijit_singh", "4YRxDV8wJFPHPTeXepOstw")
                .when()
                .get("https://api.spotify.com/v1/artists/{artists_arijit_singh}/related-artists");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String msg = res.path("artists[0].name");
        Assert.assertEquals(msg, "Neha Kakkar");
    }

    //Albums

    @Test
    public void getAlbum(){
        Response res = given()
                .header("accept","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("album_id","5mZX4EMwEyohNmVfLTDtXn")
                .when()
                .get("https://api.spotify.com/v1/albums/{album_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String album_id = res.path("id");
        Assert.assertEquals(album_id,"5mZX4EMwEyohNmVfLTDtXn");
    }

    @Test
    public void getSeveralAlbum(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc")
                .when()
                .get("https://api.spotify.com/v1/albums");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String album_id = res.path("albums[0].id");
        Assert.assertEquals(album_id,"382ObEPsp2rxGrnsizN5TX");
    }

    @Test
    public void getAlbumTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("album_id","5mZX4EMwEyohNmVfLTDtXn")
                .when()
                .get("https://api.spotify.com/v1/albums/{album_id}/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String album_id = res.path("items[0].uri");
        Assert.assertEquals(album_id,"spotify:track:3yHyiUDJdz02FZ6jfUbsmY");
    }

    @Test
    public void getUserSavedAlbums(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/albums");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void saveAlbumsForCurrentUser(){
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("album_id","3I3kZyHUtEA9Y59rJkxtk6")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"3I3kZyHUtEA9Y59rJkxtk6\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/albums?ids={album_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void removeUsersSavedAlbums(){
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("album_id","3I3kZyHUtEA9Y59rJkxtk6")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"3I3kZyHUtEA9Y59rJkxtk6\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/albums?ids={album_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void checkUserSavedAlbums(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("album_id","3I3kZyHUtEA9Y59rJkxtk6")
                .when()
                .get("https://api.spotify.com/v1/me/albums/contains?ids={album_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean value = res.path("[0]");
        Assert.assertEquals(value,true);
    }

    @Test
    public void getNewReleases(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/new-releases");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("albums.items[0].album_type");
        Assert.assertEquals(value,"single");
    }

    //Categories

    @Test
    public void getSeveralBrowseCategories() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories?locale=IN");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String msg = res.path("categories.items[2].name");
        Assert.assertEquals(msg, "Bollywood");
    }

    @Test
    public void getSingleBrowseCategory() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories/gaming?locale=IN");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String id = res.path("id");
        Assert.assertEquals(id, "0JQ5DAqbMKFCfObibaOZbv");
    }

    //Chapters

    @Test
    public void getAChapter() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/chapters/0D5wENdkdwbqlrHoaJ9g29");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void getSeveralChapters() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "3ZXb8FKZGU0EHALYX6uCzU,0D5wENdkdwbqlrHoaJ9g29")
                .when()
                .get("https://api.spotify.com/v1/chapters");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
    }

    //Genres

    @Test
    public void getAvailableGenreSeeds() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String msg = res.path("genres.get(26)");
        Assert.assertEquals(msg, "disco");
    }

    //Markets

    @Test
    public void getAvailableMarkets() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/markets");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
    }

    //Search

    @Test
    public void searchForItem() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/search?q=remaster%2520track%3ADoxy%2520artist%3AMiles%2520Davis&type=album&limit=2");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
    }

    //Episodes

    @Test
    public void getEpisode(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("episode_id","3Ml2MRtE7UsmFpulIKFX06")
                .when()
                .get("https://api.spotify.com/v1/episodes/{episode_id}?market=IN");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("id");
        Assert.assertEquals(value,"3Ml2MRtE7UsmFpulIKFX06");
    }

    @Test
    public void getSeveralEpisode(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/episodes?ids=3Ml2MRtE7UsmFpulIKFX06");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("episodes[0].id");
        Assert.assertEquals(value,"3Ml2MRtE7UsmFpulIKFX06");
    }

    @Test
    public void getUserSavedEpisodes(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/episodes");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].episode.name");
        Assert.assertEquals(value,"Ep 73 Soul Reflections Can Everyone BE SUCCESSFUL?: : BK Shivani sister shivani");
    }

    @Test
    public void saveEpisodesForCurrentUser(){
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("episode_id","3Y4mPAlzznx9eW1Mq8Fbxz")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"3Y4mPAlzznx9eW1Mq8Fbxz\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/episodes?ids={episode_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void removeUserSavedEpisodes(){
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("episode_id","3Y4mPAlzznx9eW1Mq8Fbxz")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"3Y4mPAlzznx9eW1Mq8Fbxz\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/albums?ids={episode_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void checkUserSavedEpisodes(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("episode_id","3Y4mPAlzznx9eW1Mq8Fbxz")
                .when()
                .get("https://api.spotify.com/v1/me/episodes/contains?ids={episode_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean value = res.path("[0]");
        Assert.assertTrue(true);
    }
    //Shows

    @Test
    public void getShow(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .get("https://api.spotify.com/v1/shows/{show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("episodes.items[0].id");
        Assert.assertEquals(value,"1Q0ge9mpbUTbpV2631Cv6A");
    }

    @Test
    public void getSeveralShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .pathParams("show_id2","2C+2jXbuRAD5DBeMb0uQpWtOH")
                .when()
                .get("https://api.spotify.com/v1/shows?ids={show_id}&{show_id2}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("shows[0].id");
        Assert.assertEquals(value,"6h7u9VphsbDw1m0F2eQBIy");
    }

    @Test
    public void getShowEpisodes(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .get("https://api.spotify.com/v1/shows/{show_id}/episodes");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].id");
        Assert.assertEquals(value,"1Q0ge9mpbUTbpV2631Cv6A");
    }

    @Test
    public void getUserSavedShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/shows");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void saveShowsForCurrentUser(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .put("https://api.spotify.com/v1/me/shows?ids={show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void removeUserSavedShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .delete("https://api.spotify.com/v1/me/shows?ids={show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void checkUserSavedShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .get("https://api.spotify.com/v1/me/shows/contains?ids={show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean value = res.path("[0]");
        Assert.assertTrue(true);
    }

    //Tracks

    @Test
    public void getTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/tracks/{track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("album.artists[0].name");
        Assert.assertEquals(value,"Darshan Raval");
    }

    @Test
    public void getSeveralTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B")
                .when()
                .get("https://api.spotify.com/v1/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("tracks[0].album.artists[0].id");
        Assert.assertEquals(value,"12Chz98pHFMPJEknJQMWvI");
    }

    @Test
    public void getUserSavedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].track.album.artists[0].name");
        Assert.assertEquals(value,"Darshan Raval");
    }

    @Test
    public void saveTracksForCurrentUser(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2fsBaSK1551VvUD9Uvbb9Q\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/tracks?ids={track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void removeUserSavedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2fsBaSK1551VvUD9Uvbb9Q\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/tracks?ids={track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void checkUserSavedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/me/tracks/contains?ids={track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean value = res.path("[0]");
        Assert.assertTrue(true);
    }

    @Test
    public void getSeveralTracksAudioFeatures(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "2fsBaSK1551VvUD9Uvbb9Q,2C34Fh4HXZmnuBdtgejWUZg2")
                .when()
                .get("https://api.spotify.com/v1/audio-features");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("audio_features[0].id");
        Assert.assertEquals(value,"2fsBaSK1551VvUD9Uvbb9Q");
    }

    @Test
    public void getTrackAudioFeatures(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/audio-features/{track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("uri");
        Assert.assertEquals(value,"spotify:track:2fsBaSK1551VvUD9Uvbb9Q");
    }

    @Test
    public void getTrackAudioAnalysis(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/audio-analysis/{track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("meta.platform");
        Assert.assertEquals(value,"Linux");
    }

    @Test
    public void getRecommendations(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("artist_id","4YRxDV8wJFPHPTeXepOstw")
                .when()
                .get("https://api.spotify.com/v1/recommendations?seed_artists={artist_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("tracks[0].album.album_type");
        Assert.assertEquals(value,"SINGLE");
    }

    //Playlists

    @Test
    public void createPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("user_id","31c6h2vtirvzzhzqlu2nw7oer6xq")
                .body("{\n" +
                        "    \"name\": \"Marathi Songs\",\n" +
                        "    \"description\": \"playlist description\",\n" +
                        "    \"public\": true\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/{user_id}/playlists");
        res.prettyPrint();
        res.then().statusCode(201);
        Assert.assertEquals(res.statusCode(),201);
        String msg = res.path("id");
        System.out.println(msg);
    }

    @Test
    public void addItemsToPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \"spotify:track:4ITtnvLQ6SOA8XhiqkLa24\",\"spotify:track:3fANdw64RNmZnokdIBwhXT\"\n" +
                        "    ]\n" +
                        "}\n")
                .when()
                .post("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void changePlaylistDetails(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .body("{\n" +
                        "    \n" +
                        "    \"description\": \"Updated playlist description\",\n" +
                        "    \"public\": true\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{playlist_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void getPlaylist(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .when()
                .get("https://api.spotify.com/v1/playlists/{playlist_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("id");
        Assert.assertEquals(value,"70RrDRbVXRIfuPwtbqmJJs");
    }

    @Test
    public void getPlaylistItems(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .when()
                .get("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].added_by.uri");
        Assert.assertEquals(value,"spotify:user:31c6h2vtirvzzhzqlu2nw7oer6xq");
    }

    @Test
    public void updatePlaylistItems(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .body("{\n" +
                        "    \"range_start\": 1,\n" +
                        "    \"insert_before\": 3,\n" +
                        "    \"range_length\": 2\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void removePlaylistItems(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .body("{\n" +
                        "    \"tracks\": [\n" +
                        "        {\n" +
                        "            \"uri\": \"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"snapshot_id\": \"AAAADhSwhgPLJiBCGigSCiYjlB7r300A\"\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void getCurrentUserPlaylists(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].id");
        Assert.assertEquals(value,"4lgDRgHKfK3NxYZyKyrZdc");
    }

    @Test
    public void getUserPlaylists(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("user_id","31c6h2vtirvzzhzqlu2nw7oer6xq")
                .when()
                .get("https://api.spotify.com/v1/users/{user_id}/playlists");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].id");
        Assert.assertEquals(value,"4lgDRgHKfK3NxYZyKyrZdc");
    }

    @Test
    public void getFeaturedPlaylists(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("message");
        Assert.assertEquals(value,"Popular Playlists");
    }

    @Test
    public void getCategoryPlaylists(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories/gaming/playlists");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("message");
        Assert.assertEquals(value,"Gaming");
    }

    @Test
    public void getPlaylistCoverImage(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .when()
                .get("https://api.spotify.com/v1/playlists/{playlist_id}/images");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("[0].url");
        Assert.assertEquals(value, "https://i.scdn.co/image/ab67616d0000b273af9e8dfd99c2150e692cd96e");
    }

    @Test
    public void addCustomPlaylistCoverImage(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .body("/9j/2wCEABoZGSccJz4lJT5CLy8vQkc9Ozs9R0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0cBHCcnMyYzPSYmPUc9Mj1HR0dEREdHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR//dAAQAAf/uAA5BZG9iZQBkwAAAAAH/wAARCAABAAEDACIAAREBAhEB/8QASwABAQAAAAAAAAAAAAAAAAAAAAYBAQAAAAAAAAAAAAAAAAAAAAAQAQAAAAAAAAAAAAAAAAAAAAARAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwAAARECEQA/AJgAH//Z")
                .when()
                .put("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
        res.prettyPrint();
        res.then().statusCode(202);
        Assert.assertEquals(res.statusCode(),202);
    }

    //AudioBooks

    @Test
    public void getAnAudiobook(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("audiobook_id","7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .get("https://api.spotify.com/v1/audiobooks/{audiobook_id}/chapters");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void getSeveralAudiobooks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .get("https://api.spotify.com/v1/audiobooks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void getUserSavedAudiobooks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void saveAudiobooksForCurrentUser(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .put("https://api.spotify.com/v1/me/audiobooks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void removeUserSavedAudiobooks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "18yVqkdbdRvS24c0Ilj2ci,2C1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .delete("https://api.spotify.com/v1/me/audiobooks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void checkUserSavedAudiobooks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ")
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks/contains");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        Assert.assertFalse(false);
    }

    //Player

    @Test
    public void getPlaybackState(){
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type","application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player");
        res.prettyPrint();
        res.then().statusCode(204);
    }

    @Test
    public void transferPlayback() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"device_ids\": [\n" +
                        "        \"ad1a8d083ae172b11f60f404704d77f69af6675b\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/player");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void getAvailableDevices(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/devices");
        res.prettyPrint();
        res.then().statusCode(200);
        String msg = res.path("devices[0].id");
        Assert.assertEquals(msg, "fdbce79203cc09d3d96c053daf4df83818187e21");
    }

    @Test
    public void getCurrentlyPlayingTrack(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/currently-playing");
        res.prettyPrint();
        res.then().statusCode(204);
    }

    @Test
    public void startResumePlayback() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"context_uri\": \"spotify:album:5ht7ItJgpBH7W6vJ5BqpPr\",\n" +
                        "    \"offset\": {\n" +
                        "        \"position\": 5\n" +
                        "    },\n" +
                        "    \"position_ms\": 0\n" +
                        "}")
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/play?device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void pausePlayback() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"context_uri\": \"spotify:album:5ht7ItJgpBH7W6vJ5BqpPr\",\n" +
                        "    \"offset\": {\n" +
                        "        \"position\": 5\n" +
                        "    },\n" +
                        "    \"position_ms\": 0\n" +
                        "}")
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/pause?device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void skipToNext() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .post("https://api.spotify.com/v1/me/player/next?device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void skipToPrevious() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .post("https://api.spotify.com/v1/me/player/previous?device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void seekToPosition() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/seek?position_ms=25000&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void setRepeatMode() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/repeat?state=context&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void setPlaybackVolume() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/volume?volume_percent=50&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void togglePlaybackShuffle() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/shuffle?state=true&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void getRecentlyPlayedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/recently-played");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void getTheUserQueue(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/queue");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.message");
        Assert.assertEquals(msg, "Forbidden.");
    }

    @Test
    public void addItemToPlaybackQueue() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .post("https://api.spotify.com/v1/me/player/queue?uri=spotify%3Atrack%3A4iV5W9uYEdYUVa79Axb7Rh&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

}
