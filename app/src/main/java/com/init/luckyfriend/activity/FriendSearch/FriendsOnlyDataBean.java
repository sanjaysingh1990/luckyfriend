package com.init.luckyfriend.activity.FriendSearch;

/**
 * Created by user on 2/19/2016.
 */
public class FriendsOnlyDataBean {
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Number getLikecount() {
        return likecount;
    }

    public void setLikecount(Number likecount) {
        this.likecount = likecount;
    }

    public Number getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Number commentcount) {
        this.commentcount = commentcount;
    }

    private String caption,imageurl;
    private Number likecount,commentcount;

}
