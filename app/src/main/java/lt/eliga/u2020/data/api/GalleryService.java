package lt.eliga.u2020.data.api;

import lt.eliga.u2020.data.api.model.response.Gallery;
import lt.eliga.u2020.data.api.model.request.Section;
import lt.eliga.u2020.data.api.model.request.Sort;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GalleryService {
    @GET("/gallery/{section}/{sort}/{page}")
    Observable<Gallery> listGallery( @Path("section") Section section,
                                     @Path("sort") Sort sort,
                                     @Path("page") int page);
}
