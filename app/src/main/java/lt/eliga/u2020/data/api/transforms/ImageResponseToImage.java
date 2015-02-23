package lt.eliga.u2020.data.api.transforms;

import lt.eliga.u2020.data.api.model.response.Image;
import lt.eliga.u2020.data.api.model.response.ImageResponse;

import rx.functions.Func1;

public class ImageResponseToImage implements Func1<ImageResponse, Image> {
    @Override
    public Image call(ImageResponse imageResponse) {
        return imageResponse.data;
    }
}
