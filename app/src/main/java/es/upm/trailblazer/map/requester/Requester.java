package es.upm.trailblazer.map.requester;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class Requester {

    private static final String BASE_URL = "https://api.opentripmap.com/0.1/";
    private static final String LANGUAGE = "en";
    private static final int RADIUS = 20000;
    private static final String SOURCE_OBJECT = "osm";
    private static final String KINDS = "natural";
    private static final String ACCEPT = "application/json";

    private static final String AUTHORIZATION = "5ae2e3f221c38a28845f05b6395273e118cdead450125b155a2f6501";


    private Retrofit retrofit;
    private OpenTripMap openTripMap;

    public Requester() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.openTripMap = retrofit.create(OpenTripMap.class);
    }

    public void getPlaces(double latitude, double longitude, Callback callback) throws IOException {
        Call call = this.openTripMap.getPlaces(ACCEPT, LANGUAGE, AUTHORIZATION, RADIUS, longitude, latitude,
                SOURCE_OBJECT, SOURCE_OBJECT, KINDS);
        call.enqueue(callback);
    }
}
