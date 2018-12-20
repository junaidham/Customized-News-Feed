package tech.ducletran.customizednewsfeedapp.OtherResource;

import java.util.Date;

public class City {
    private String imageURL;
    private String name;
    private String articleURL;
    private String description;
    private String country;
    private boolean expanding;



    public City(String imageURL,String name, String articleURL,String description,
               String country) {
        this.articleURL = articleURL;
        this.description = description;
        this.imageURL = imageURL;
        this.name = name;
        this.country = country;
        this.expanding = false;
    }

    /*
     * Methods to return the information of the article/new
     */
    public String getName() {return this.name;}
    public String getDescription() {return this.description;}
    public String getImageURL() {return this.imageURL;}
    public String getArticleURL() {return this.articleURL;}
    public String getCountry() {return this.country;}
    public boolean getExpanding() {return this.expanding;}

    /** Method to change expanding view **/
    public void changeExpanding() {
        this.expanding = !this.expanding;
    }
}
