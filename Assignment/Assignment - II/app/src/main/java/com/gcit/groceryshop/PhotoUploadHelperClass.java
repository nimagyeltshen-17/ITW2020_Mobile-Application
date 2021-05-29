package com.gcit.groceryshop;

public class PhotoUploadHelperClass {
    private String Title, ShopName, Contact, PhotoUri;
    public PhotoUploadHelperClass(){

    }

    public PhotoUploadHelperClass(String Title, String ShopName, String Contact, String PhotoUri){
        if(Title.trim().equals("")){
            Title = "No Title";
        }
        if(ShopName.trim().equals("")){
            ShopName = "No Shop name";
        }
        if(Contact.trim().equals("")){
            Contact = "No contact";
        }
        this.Title = Title;
        this.ShopName = ShopName;
        this.Contact = Contact;
        this.PhotoUri = PhotoUri;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getPhotoUri() {
        return PhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        PhotoUri = photoUri;
    }


}