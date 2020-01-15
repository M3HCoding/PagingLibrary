package dev.zmq.paginglibrary;

public class URL
{
    public static final String BaseUrl="https://api.stackexchange.com/2.2/";
    public static final String Answers="answers";

    //the size of a page that we want
    public static final int PAGE_SIZE = 50;

    //we will start from the first page which is 1
    public static final int FIRST_PAGE = 1;

    //we need to fetch from stackoverflow
    public static final String SITE_NAME = "stackoverflow";
}
