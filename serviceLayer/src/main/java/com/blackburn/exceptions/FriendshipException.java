package com.blackburn.exceptions;

public class FriendshipException extends RuntimeException
{
    protected FriendshipException(String message){
        super(message);
    }

    public static FriendshipException AlreadyFriends(){
        return new FriendshipException("The cats are already friends");
    }

    public static FriendshipException BefriendsItself(){
        return new FriendshipException("The cat cannot befriend itself");
    }

    public static FriendshipException AreNotFriends(){
        return new FriendshipException("The cats are not friends");
    }

    public static FriendshipException SameUserRequest(){
        return new FriendshipException("Cannot send request to the same user");
    }
}
