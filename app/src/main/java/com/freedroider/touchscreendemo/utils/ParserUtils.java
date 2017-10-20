package com.freedroider.touchscreendemo.utils;

import com.freedroider.touchscreendemo.model.ControlAction;
import com.freedroider.touchscreendemo.model.Ratio;
import com.google.gson.Gson;

public class ParserUtils {
    public static ControlAction sActionCoordinates(String model) {
        Gson gson = new Gson();
        return gson.fromJson(model, ControlAction.class);
    }

    public static Ratio sActionSize(String model) {
        Gson gson = new Gson();
        return gson.fromJson(model, Ratio.class);
    }
}
