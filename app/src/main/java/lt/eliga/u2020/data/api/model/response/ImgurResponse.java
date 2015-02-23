package lt.eliga.u2020.data.api.model.response;

public abstract class ImgurResponse {
    public final int status;
    public final boolean success;

    public ImgurResponse(int status, boolean success) {
        this.status = status;
        this.success = success;
    }
}
