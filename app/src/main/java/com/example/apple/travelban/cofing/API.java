package com.example.apple.travelban.cofing;

/**
 * Created by apple on 15/8/14.
 */
public class API {
    public static class URL {
        public static final String GetTrainList="http://apis.baidu.com/qunar/qunar_train_service/s2ssearch";
        public static final String PLACE_URL = "http://apis.baidu.com/apistore/attractions/spot";
        public static final String TRAIN_URL = "http://apis.baidu.com/qunar/qunar_train_service/s2ssearch";
        public static final String AdList="http://apis.baidu.com/qunartravel/travellist/travellist";

    }

    public static class PLACE_KEY {
        public static final String API_KEY = "a27987f79777846ce2ac7906c01a1a7e";
        public static final int ERROR = 0;
        public static final String STATUS = "Success";
    }
    public static class CITY_KEY {
        public static final String API_KEY = "a27987f79777846ce2ac7906c01a1a7e";
        public static final int ERROR = 0;
        public static final String STATUS = "Success";
    }

    public static class TRAIN_KEY {
        public static final String API_KEY = "a27987f79777846ce2ac7906c01a1a7e";
        public static final String RET = "ret";
        public static final String DATA = "data";
        public static final String TRAINLIST = "trainList";
    }
    public static class KEY{
        public static final String API_KEY="a27987f79777846ce2ac7906c01a1a7e";
        public static final String RET="ret";
        public static final String DATA="data";
        public static final String TRAINLIST="trainList";
    }
    public static class HOTEL_KEY{
        public static final String SHOWAPI_APPID="100";
        public static final String SHOWAPI_SIGN="698d51a19d8a121ce581499d7b701668";
        public static final int SHOWAPI_RES_CODE=0;

    }
    public static class AD_KEY{
        public static final boolean RET=true;
        public static final String ERRMSG ="success";
        public static final int ERRCODE=0;

    }
    public static class CODE {
        public static final int SUCCEED = 200;
        public static final int Failure = 0;
        public static final int PERMISSION_DENIED = 213;
    }
}
