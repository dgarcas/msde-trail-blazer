package es.upm.trailblazer.map.requester;

import com.google.gson.JsonObject;

import java.util.List;

import es.upm.trailblazer.map.requester.schemas.Feature;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenTripMap {

    @GET("{lang}/places/radius")
    Call<JsonObject> getPlaces(@Header("Accept") String accept, @Path("lang") String language,
                               @Query("apikey") String apikey, @Query("radius") int radius,
                               @Query("lon") Double longitude, @Query("lat") Double latitude,
                               @Query("src_geom") String srcGeom, @Query("src_attr") String srcAttr,
                               @Query("kinds") String kinds);
}