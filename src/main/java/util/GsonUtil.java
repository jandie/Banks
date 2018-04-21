package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import inter.domain.InterTransaction;

import java.io.Serializable;

public class GsonUtil {
    public <T> T decode(String json, Class<InterTransaction> type) {
        Gson gson = new GsonBuilder().create();

        return gson.<T>fromJson(json, type.getClass());
    }

    public String encode(Serializable object) {
        Gson gson = new GsonBuilder().create();

        return gson.toJson(object);
    }
}
