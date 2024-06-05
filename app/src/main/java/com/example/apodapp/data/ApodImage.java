package com.example.apodapp.data;

/**
 * Třída ApodImage reprezentuje data obrázku NASA APOD.
 */
public class ApodImage {
    private String date;         // Datum obrázku APOD.
    private String explanation;  // Popis obrázku APOD.
    private String url;          // URL adresa obrázku APOD.
    private String title;        // Titulek obrázku APOD.

    /**
     * Vrací datum obrázku APOD.
     * @return Datum obrázku ve formátu String.
     */
    public String getDate() {
        return date;
    }

    /**
     * Nastaví datum obrázku APOD.
     * @param date Datum obrázku ve formátu String.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Vrací popis obrázku APOD.
     * @return Popis obrázku ve formátu String.
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Nastaví popis obrázku APOD.
     * @param explanation Popis obrázku ve formátu String.
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * Vrací URL adresu obrázku APOD.
     * @return URL adresa obrázku ve formátu String.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Nastaví URL adresu obrázku APOD.
     * @param url URL adresa obrázku ve formátu String.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Vrací titulek obrázku APOD.
     * @return Titulek obrázku ve formátu String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Nastaví titulek obrázku APOD.
     * @param title Titulek obrázku ve formátu String.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
