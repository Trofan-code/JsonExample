package com.example.jsonexample.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetworkUtils {

    private static final String vk_api_base_URL = "https://api.vk.com";
    private static final String vk_users_get = "/method/users.get";
    private static final String param_user_id = "user_ids";
    private static final String param_version = "v";
    private static final  String access_token = "access_token";


    public static URL generateURL(String userId) {
        Uri builtUri = Uri.parse(vk_api_base_URL + vk_users_get)
                .buildUpon()
                .appendQueryParameter(param_user_id, userId)
                .appendQueryParameter(param_version, "5.8")
                .appendQueryParameter(access_token, "f5d94d86f5d94d86f5d94d86fcf5a96756ff5d9f5d94d86abbc287715342f5d763a8681")
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (UnknownHostException e){
            return null;
        }finally {
                urlConnection.disconnect();
            }

        }
    }
