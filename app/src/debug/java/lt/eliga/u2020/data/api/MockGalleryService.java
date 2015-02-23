package lt.eliga.u2020.data.api;

import java.util.List;

import javax.inject.Inject;

import lt.eliga.u2020.data.api.model.request.Section;
import lt.eliga.u2020.data.api.model.request.Sort;
import lt.eliga.u2020.data.api.model.response.Gallery;
import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.ui.ApplicationScope;
import rx.Observable;

@ApplicationScope
final class MockGalleryService implements GalleryService {
    private static final Gallery BAD_REQUEST = new Gallery(200, false, null);
    private static final int PAGE_SIZE = 50;

    private final ServerDatabase serverDatabase;

    @Inject
    MockGalleryService(ServerDatabase serverDatabase) {
        this.serverDatabase = serverDatabase;
    }

    @Override
    public Observable<Gallery> listGallery(Section section, Sort sort, int page) {
        // Fetch desired section.
        List<Image> images = serverDatabase.getImagesForSection(section);
        if (images == null) {
            return Observable.just(BAD_REQUEST);
        }

        // Figure out proper list subset.
        int pageStart = (page - 1) * PAGE_SIZE;
        if (pageStart >= images.size() || pageStart < 0) {
            return Observable.just(BAD_REQUEST);
        }
        int pageEnd = Math.min(pageStart + PAGE_SIZE, images.size());

        // Sort and trim images.
        SortUtil.sort(images, sort);
        images = images.subList(pageStart, pageEnd);

        return Observable.just(new Gallery(200, true, images));
    }
}
