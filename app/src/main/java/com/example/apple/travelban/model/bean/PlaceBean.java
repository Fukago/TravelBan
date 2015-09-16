package com.example.apple.travelban.model.bean;

import java.util.List;

/**
 * Created by apple on 15/8/16.
 */
public class PlaceBean {
    public int error;
    public String status;
    public String date;

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

    public CityResult getResult() {
        return result;
    }

    public void setResult(CityResult result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CityResult result;

    public class CityResult {
        public int cityid;
        public String cityname;
        public CityLocation location;
        public String star;
        public String url;

        public int getCityid() {
            return cityid;
        }

        public void setCityid(int cityid) {
            this.cityid = cityid;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Plan> getItineraries() {
            return itineraries;
        }

        public void setItineraries(List<Plan> itineraries) {
            this.itineraries = itineraries;
        }

        public CityLocation getLocation() {
            return location;
        }

        public void setLocation(CityLocation location) {
            this.location = location;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String description;
        public List<Plan> itineraries;

        public class Plan {
            public String name;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<Detail> getItineraries() {
                return itineraries;
            }

            public void setItineraries(List<Detail> itineraries) {
                this.itineraries = itineraries;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String description;
            public List<Detail> itineraries;

            public class Detail {
                public List<Place> path;

                public String getAccommodation() {
                    return accommodation;
                }

                public void setAccommodation(String accommodation) {
                    this.accommodation = accommodation;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getDinning() {
                    return dinning;
                }

                public void setDinning(String dinning) {
                    this.dinning = dinning;
                }

                public List<Place> getPath() {
                    return path;
                }

                public void setPath(List<Place> path) {
                    this.path = path;
                }

                public String description;
                public String dinning;
                public String accommodation;

                public class Place {
                    public String name;

                    public String getDetail() {
                        return detail;
                    }

                    public void setDetail(String detail) {
                        this.detail = detail;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String detail;
                }
            }
        }

        public class CityLocation {
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
    }
}
