package com.inerun.courier.base;

import android.content.Context;
import android.view.View;

import com.inerun.courier.R;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by vinay on 19/06/18.
 */

public abstract class ClickListener implements View.OnClickListener {
    Context context;

    public ClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        try {
            click(v);
        } catch (SQLException e) {
            e.printStackTrace();
            String message = context.getString(R.string.exception_alert_message_sqllite_exception);
            onError(message);


        } catch (IOException e) {
            e.printStackTrace();
            String message = context.getString(R.string.server_exception_error_message_ioexception);
            onError(message);


        } catch (NumberFormatException e) {
            e.printStackTrace();
            String message = context.getString(R.string.server_exception_error_message_numberformat);
            onError(message);


        } catch (IllegalStateException e) {
            e.printStackTrace();
            String message = context.getString(R.string.exception_alert_message_illegalstate_exception);;
            onError(message);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String message = context.getString(R.string.server_exception_error_message_illegalargument);
            onError(message);


        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            String message = context.getString(R.string.server_exception_error_message_indexoutofbound);
            onError(message);


        } catch (NullPointerException e) {
            e.printStackTrace();
            String message = context.getString(R.string.server_exception_error_message_nullpointerr);
            onError(message);


        } catch (RuntimeException e) {
            e.printStackTrace();
            String message = e.getMessage();
            onError(message);


        } catch (Exception e) {
            String message = e.toString();
            message= context.getString(R.string.foodmenu_error_unknown_error)+message;
            onError(message);
        }

    }

    private void onError(String message) {
        showException(message);
    }


    public void showException(String message) {
        SweetAlertUtil.showErrorMessage(context, message);
    }


    abstract public void click(View v) throws  Exception;

}
