package com.example.movie_mvvm.Data.Repository;

enum Status {
    RUNNING,
    SUCCESS,
    FAILED
}

public class NetworkState {

    public String msg;
    private Status status;

    private NetworkState(Status status, String msg) {
        this.status=status;
        this.msg=msg;
    }

    public static final class Companion{

        public static NetworkState LOADED=new NetworkState(Status.SUCCESS,"Success");
        public static NetworkState LOADING=new NetworkState(Status.RUNNING, "Running");
        public static NetworkState ERROR=new NetworkState(Status.FAILED, "Something went wrong");
        public static NetworkState ENDOFLIST=new NetworkState(Status.FAILED,"You have reached the end");
    }
}
