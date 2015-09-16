package com.example.apple.travelban.model.bean;

import java.util.List;

/**
 * Created by apple on 15/8/10.
 */

public class TrainBean {
    public String ret;
    public TrainData data;

    public TrainData getData() {
        return data;
    }

    public void setData(TrainData data) {
        this.data = data;
    }

    public class TrainData {
        public List<TrainList> trainList;

        public List<TrainList> getTrainList() {
            return trainList;
        }

        public void setTrainList(List<TrainList> trainList) {
            this.trainList = trainList;
        }


    }

    public class TrainList {
        public String trainType;
        public String trainNo;
        public String from;
        public String to;
        public String startTime;
        public String endTime;
        public String duration;
        public List<SeatInfos> seatInfos;


        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public List<SeatInfos> getSeatInfos() {
            return seatInfos;
        }

        public void setSeatInfos(List<SeatInfos> seatInfos) {
            this.seatInfos = seatInfos;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getTrainNo() {
            return trainNo;
        }

        public void setTrainNo(String trainNo) {
            this.trainNo = trainNo;
        }

        public String getTrainType() {
            return trainType;
        }

        public void setTrainType(String trainType) {
            this.trainType = trainType;
        }
    }

    public class SeatInfos {
        public String seat;
        public String seatPrice;
        public String remainNum;

        public String getRemainNum() {
            return remainNum;
        }

        public void setRemainNum(String remainNum) {
            this.remainNum = remainNum;
        }

        public String getSeat() {
            return seat;
        }

        public void setSeat(String seat) {
            this.seat = seat;
        }

        public String getSeatPrice() {
            return seatPrice;
        }

        public void setSeatPrice(String seatPrice) {
            this.seatPrice = seatPrice;
        }
    }
}



