public enum Paths {
    get_names("get-names"), get_string("get-string"), check("check");

    private String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath () {
        return "/fm1/" + this.path;
    }
}
