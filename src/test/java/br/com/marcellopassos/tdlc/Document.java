package br.com.marcellopassos.tdlc;

public class Document implements Identifiable<String> {

    private String hash;

    private Long size;

    private String path;

    private String mimeType;

    public Document() {
    }

    public Document(String hash, Long size, String path, String mimeType) {
        this.hash = hash;
        this.size = size;
        this.path = path;
        this.mimeType = mimeType;
    }

    @Override
    public String getId() {
        return this.hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

}
