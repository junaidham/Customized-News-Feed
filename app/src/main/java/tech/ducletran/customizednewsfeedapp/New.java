package tech.ducletran.customizednewsfeedapp;

import java.util.Date;

public class New {
    private String imageURL;
    private String title;
    private String articleURL;
    private String timePublished;
    private String description;
    private String writer;
    private String content;
    private String source;
    private Date date;

    public New(String imageURL,String title,String articleURL,String timePublished,String description,
               String writer, String content,String source) {
        this(imageURL,title,articleURL,timePublished,description,writer,content,source,new Date());

    }

    public New(String imageURL,String title,String articleURL,String timePublished,String description,
               String writer, String content,String source, Date date) {
        this.articleURL = articleURL;
        this.title = title;
        this.timePublished = timePublished;
        this.description = description;
        this.writer = writer;
        this.content = content;
        this.imageURL = imageURL;
        this.source = source;
        this.date = date;
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
    public String getSource() {return this.source;}
    public Date getDate() {return this.date;}
}
