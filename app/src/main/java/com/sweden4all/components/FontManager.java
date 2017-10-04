package com.sweden4all.components;

import android.content.Context;
import android.graphics.Typeface;

import java.util.LinkedHashMap;

public class FontManager {

    private static LinkedHashMap<Integer, Typeface> fontCache = new LinkedHashMap<>();

    public static Typeface getTypeface(Context context, int fontIndex) {
        fillCache(context);

        return fontCache.get(fontIndex);

        /*if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontName, typeface);
        }*/
    }

    private static void fillCache(Context context) {
        fontCache.put(0, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Black.ttf"));
        fontCache.put(1, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BlackItalic.ttf"));
        fontCache.put(2, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf"));
        fontCache.put(3, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BoldItalic.ttf"));
        fontCache.put(4, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Italic.ttf"));
        fontCache.put(5, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
        fontCache.put(6, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-LightItalic.ttf"));
        fontCache.put(7, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"));
        fontCache.put(8, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf"));
        fontCache.put(9, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
        fontCache.put(10, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf"));
        fontCache.put(11, Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-ThinItalic.ttf"));
    }
}
