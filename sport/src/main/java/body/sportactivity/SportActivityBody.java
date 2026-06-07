package body.sportactivity;

import org.json.simple.JSONObject;

public class SportActivityBody {
    public JSONObject createSportActivityData(String sportCategoryId, int cityId, String title, 
                                               String description, int slot, int price, String address, 
                                               String activityDate, String startTime, String endTime, 
                                               String mapUrl) {
        JSONObject body = new JSONObject();
        body.put("sport_category_id", sportCategoryId);
        body.put("city_id", cityId);
        body.put("title", title);
        body.put("description", description);
        body.put("slot", slot);
        body.put("price", price);
        body.put("address", address);
        body.put("activity_date", activityDate);
        body.put("start_time", startTime);
        body.put("end_time", endTime);
        body.put("map_url", mapUrl);
        return body;
    }

    public JSONObject updateSportActivityData(String sportCategoryId, int cityId, String title, 
                                               String description, int slot, int price, String address, 
                                               String activityDate, String startTime, String endTime, 
                                               String mapUrl) {
        JSONObject body = new JSONObject();
        body.put("sport_category_id", sportCategoryId);
        body.put("city_id", cityId);
        body.put("title", title);
        body.put("description", description);
        body.put("slot", slot);
        body.put("price", price);
        body.put("address", address);
        body.put("activity_date", activityDate);
        body.put("start_time", startTime);
        body.put("end_time", endTime);
        body.put("map_url", mapUrl);
        return body;
    }
}
