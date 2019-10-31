package com.example.asus.PerfectCircleITProject;

public class OneGameCard {

    private String image1Url;
    private String image2Url;
    private String difficultyText;
    private String dateText;
    private String markText;

    public OneGameCard(String image1Url, String image2Url, String difficultyText, String dateText, String markText){
        this.image1Url=image1Url;
        this.image2Url=image2Url;
        this.difficultyText=difficultyText;
        this.dateText=dateText;
        this.markText=markText;
    }

    public OneGameCard(String difficultyText, String dateText, String markText){
        this.difficultyText=difficultyText;
        this.dateText=dateText;
        this.markText=markText;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public String getDifficultyText() {
        return difficultyText;
    }

    public String getDateText() {
        return dateText;
    }

    public String getMarkText() {
        return markText;
    }
}
