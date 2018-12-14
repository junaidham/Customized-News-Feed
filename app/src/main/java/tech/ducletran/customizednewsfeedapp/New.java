package tech.ducletran.customizednewsfeedapp;

public class New {
    private String imageURL;
    private String title;
    private String articleURL;
    private String timePublished;
    private String description;
    private String writer;
    private String content;

    public New(String imageURL,String title,String articleURL,String timePublished,String description,
               String writer, String content) {
        this.articleURL = articleURL;
        this.title = title;
        this.timePublished = timePublished;
        this.description = description;
        this.writer = writer;
        this.content = content;
    }

    /*
     * Methods to return the information of the article/new
     */
    public String getImageURL() {return this.imageURL;}
    public String getTitle() {return this.title;}
    public String getArticleURL() {return this.articleURL;}
    public String getTimePublished() {return this.timePublished;}
    public String getDescription() {return this.description;}
    public String getWriter() {return this.writer;}
    public String getContent() {return this.content;}
}
