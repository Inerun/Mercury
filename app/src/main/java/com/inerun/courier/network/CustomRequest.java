package com.inerun.courier.network;

/**
 * Created by vinay on 02/12/16.
 */

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.victor.loading.rotate.RotateLoading;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomRequest extends Request<String>{

    private  RotateLoading progressBar;
    private Listener<String> listener;
    private Map<String, String> params;

    public CustomRequest(String url, Map<String, String> params,
                         Listener<String> reponseListener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public CustomRequest(int method, String url, Map<String, String> params,
                         Listener<String> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    } public CustomRequest(int method, RotateLoading progressBar, String url, Map<String, String> params,
                           Listener<String> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.progressBar = progressBar;
    }

    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params;
    };

    @Override
    protected void deliverResponse(String response) {
        if(listener!=null)
        listener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Log.i("REsponse",""+jsonString);
//            return Response.success(new String(jsonString),
//                    HttpHeaderParser.parseCacheHeaders(response));


            return Response.success(new String(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

}