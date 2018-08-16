package com.olgusha.flashchatnewfirebase;

// modal class for visual chat
//dlya vzaimodeistviya s firebase mojno set on instance of own(наших собственных) class satisfy (удовлетворять)
//  flow constrains(sootvetstvovat ogranicheniyam) :
// imet default constractor
// define public getters for the properties

public class InstantMessage {

    private String message ;
    private String author;

    public InstantMessage (String cmessage , String  cauthor)
    {
      this.message= cmessage;
      this.author=cauthor;

    }

    public  InstantMessage(){}

    public  String getMessage(){
        return  message ;
    }

    public String getAuthor() {
        return author;
    }
}
