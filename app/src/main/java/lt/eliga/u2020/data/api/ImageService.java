package lt.eliga.u2020.data.api;

import lt.eliga.u2020.data.api.model.response.ImageResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface ImageService {
    @GET("/image/{id}")
    Observable<ImageResponse> image(@Path("id") String id);
}
