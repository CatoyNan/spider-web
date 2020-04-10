package top.catoy;
public class Param {

    public Param(String url,Integer size) {
        this.url = url;
        this.size = size;
    }

    private String url;
    private Integer size;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
}