package com.example.apple.travelban.model.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 *
 * 用户发起话题
 */
public class Topic extends BmobObject implements Serializable{

    private User author;
    private String title;
    private String content;
    private String location;
    private String placeName;
    private String time;
    private List<String> pic;
    private Integer good;
    private Integer bad;
    private int share;
    private int commentNum;
    private boolean isPass;
    private boolean myFav;//收藏
    private boolean myLove;//赞
    private boolean myHate;
    private BmobFile Contentfigureurl;
    private BmobRelation relation;

    public Topic(){
        good = 0;
        bad = 0;
        share = 0;
        commentNum = 0;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public BmobFile getContentfigureurl() {
        return Contentfigureurl;
    }

    public void setContentfigureurl(BmobFile contentfigureurl) {
        Contentfigureurl = contentfigureurl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getBad() {
        return bad;
    }

    public void setBad(Integer bad) {
        this.bad = bad;
    }

    public boolean isMyHate() {
        return myHate;
    }

    public void setMyHate(boolean myHate) {
        this.myHate = myHate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setIsPass(boolean isPass) {
        this.isPass = isPass;
    }

    public boolean isMyFav() {
        return myFav;
    }

    public void setMyFav(boolean myFav) {
        this.myFav = myFav;
    }

    public boolean isMyLove() {
        return myLove;
    }

    public void setMyLove(boolean myLove) {
        this.myLove = myLove;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }
}
