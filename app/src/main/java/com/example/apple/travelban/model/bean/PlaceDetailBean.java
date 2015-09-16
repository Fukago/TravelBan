package com.example.apple.travelban.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 15/8/14.
 */
public class PlaceDetailBean {
    public int error;
    public String status;
    public String date;
    public PlaceResult result;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public PlaceResult getResult() {
        return result;
    }

    public void setResult(PlaceResult result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public class PlaceResult implements Serializable {
        public String name;
        public PlaceLocation location;
        public String telephone;
        public String star;
        public String url;
        public String Abstract;
        public String description;
        public PlaceTicketInfo ticket_info;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbstract() {
            return Abstract;
        }

        public void setAbstract(String anAbstract) {
            Abstract = anAbstract;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public PlaceLocation getLocation() {
            return location;
        }

        public void setLocation(PlaceLocation location) {
            this.location = location;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public PlaceTicketInfo getTicket_info() {
            return ticket_info;
        }

        public void setTicket_info(PlaceTicketInfo ticket_info) {
            this.ticket_info = ticket_info;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

       public class PlaceAttention{
            public String name;
            public String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
      public   class PlaceLocation{
            public double lng;
            public double lat;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
       public class PlaceTicketInfo{
            public String price;
            public String open_time;
            public List<PlaceAttention> attention;

           public List<PlaceAttention> getAttention() {
               return attention;
           }

           public void setAttention(List<PlaceAttention> attention) {
               this.attention = attention;
           }

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
