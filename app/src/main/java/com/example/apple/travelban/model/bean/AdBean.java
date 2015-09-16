package com.example.apple.travelban.model.bean;

import java.util.List;

/**
 * Created by apple on 15/8/24.
 */
public class AdBean {
    public boolean ret;
    public int errcode;
    public String errmsg;
    public int ver;
    public AdData data;

    public AdData getData() {
        return data;
    }

    public void setData(AdData data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public  class AdData{
        public List<Books> books;
        public int count;

        public List<Books> getBooksList() {
            return books;
        }

        public void setBooksList(List<Books> books) {
            this.books = books;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public class Books{
            public String bookUrl;
            public String title;
            public String headImage;
            public String userName;
            public String userHeadImg;
            public String startTime;
            public int routeDays;
            public int bookImgNum;
            public int viewCount;
            public int likeCount;
            public int commentCount;
            public String text;
            public boolean elite;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getBookImgNum() {
                return bookImgNum;
            }

            public void setBookImgNum(int bookImgNum) {
                this.bookImgNum = bookImgNum;
            }

            public String getBookUrl() {
                return bookUrl;
            }

            public void setBookUrl(String bookUrl) {
                this.bookUrl = bookUrl;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public boolean isElite() {
                return elite;
            }

            public void setElite(boolean elite) {
                this.elite = elite;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public int getRouteDays() {
                return routeDays;
            }

            public void setRouteDays(int routeDays) {
                this.routeDays = routeDays;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUserHeadImg() {
                return userHeadImg;
            }

            public void setUserHeadImg(String userHeadImg) {
                this.userHeadImg = userHeadImg;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getViewCount() {
                return viewCount;
            }

            public void setViewCount(int viewCount) {
                this.viewCount = viewCount;
            }
        }
    }
}
