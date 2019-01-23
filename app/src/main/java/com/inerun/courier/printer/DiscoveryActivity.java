package com.inerun.courier.printer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.discovery.Discovery;
import com.epson.epos2.discovery.DiscoveryListener;
import com.epson.epos2.discovery.FilterOption;
import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class DiscoveryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context mContext = null;
    private ArrayList<HashMap<String, String>> mPrinterList = null;
    private SimpleAdapter mPrinterListAdapter = null;
    private FilterOption mFilterOption = null;
    private TextView status;

    static {
        System.loadLibrary("epos2");
    }
    @Override
    public int customSetContentView() {
        return R.layout.activity_discovery;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {
        mContext = this;
//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(mContext));
        Button button = (Button)findViewById(R.id.btnRestart);
        button.setOnClickListener(this);
         status = (TextView)findViewById(R.id.statusTv);


        mPrinterList = new ArrayList<HashMap<String, String>>();
        mPrinterListAdapter = new SimpleAdapter(this, mPrinterList, R.layout.printer_list_item,
                new String[] { "PrinterName", "Target" },
                new int[] { R.id.PrinterName, R.id.Target });
        ListView list = (ListView)findViewById(R.id.lstReceiveData);
        list.setAdapter(mPrinterListAdapter);
        list.setOnItemClickListener(this);

        mFilterOption = new FilterOption();
        mFilterOption.setDeviceType(Discovery.TYPE_PRINTER);

        mFilterOption.setEpsonFilter(Discovery.FILTER_NAME);
        mFilterOption.setPortType(Discovery.PORTTYPE_ALL);
        setStatus("customOnCreate");
//        try {
////            if(Discovery.isRunning()) {
//                Discovery.stop();
////            }
//            }
//            catch (Exception e) {
////                ShowMsg.showException(e, "customOnCreatestop", mContext);
//            }

        try {
            setStatus("customOnCreate Discovery.start");
//            setStatus("customOnCreate isRunning "+Discovery.isRunning());
            //fixed to resolve printer not showing issue in v2.44
            stopService(new Intent(this,
                    PrinterService.class));

            Discovery.start(this, mFilterOption, mDiscoveryListener);
            setStatus("customOnCreate Discovery.start Done");
        }  catch (Epos2Exception e) {
            setStatus("customOnCreate Epos2Exception "+ e.getErrorStatus()+" "+e.getMessage());

//                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
//                    ShowMsg.showException(e, "restartDiscoverystop", mContext);

//                }

        }
        catch (Exception e) {
            setStatus("customOnCreate Discovery.start Exception"+e.toString());
//            ShowMsg.showException(e, "customOnCreatestart", mContext);
        }catch (UnsatisfiedLinkError e) {
            setStatus("customOnCreate Discovery.start Exception"+e.toString());
e.printStackTrace();
//            ShowMsg.showException(e, "customOnCreatestart", mContext);
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

        setStatus("onDestroy");
        while (true) {
            try {
                setStatus("onDestroy Discovery.stop ");
                    Discovery.stop();

                break;
            }
            catch (Epos2Exception e) {
                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {

                    break;
                }
            }catch (Exception e) {

                    break;

            }catch (UnsatisfiedLinkError e) {

                    break;

            }
        }

        mFilterOption = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRestart:
                restartDiscovery();
                break;

            default:
                // Do nothing
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();

        HashMap<String, String> item  = mPrinterList.get(position);
        intent.putExtra(getString(R.string.title_target), item.get("Target"));

        setResult(RESULT_OK, intent);

        finish();
    }

    private void restartDiscovery() {

//        while (true) {
            try {
                setStatus("restartDiscovery Discovery.stop");
//                if(Discovery.isRunning()) {
                    Discovery.stop();
                setStatus("restartDiscovery Discovery.stop Done");
//                }

            }
            catch (Epos2Exception e) {
                setStatus("restartDiscovery Epos2Exception "+ e.getErrorStatus()+" "+e.getMessage());
//                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
//                    ShowMsg.showException(e, "restartDiscoverystop", mContext);

//                }

            }catch (Exception e) {
                setStatus("restartDiscovery Epos2Exception "+e.getMessage());
//                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
//                     ShowMsg.showException(e, "restartDiscoverystop", mContext);
//                ShowMsg.showException(e, "restartDiscoverystop", mContext);
//                }

            }catch (UnsatisfiedLinkError e) {
                setStatus("customOnCreate Discovery.start Exception"+e.toString());

//            ShowMsg.showException(e, "customOnCreatestart", mContext);
            }
//        }

        mPrinterList.clear();
        mPrinterListAdapter.notifyDataSetChanged();

        try {
            setStatus("restartDiscovery Discovery.start");
            Discovery.start(this, mFilterOption, mDiscoveryListener);
            setStatus("restartDiscovery Discovery.start Done");
        }
        catch (Exception e) {
            setStatus("restartDiscovery Exception "+e.getMessage());
//            ShowMsg.showException(e, "restartDiscoverystart", mContext);
        }catch (UnsatisfiedLinkError e) {
            setStatus("customOnCreate Discovery.start Exception"+e.toString());

//            ShowMsg.showException(e, "customOnCreatestart", mContext);
        }
    }

    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {


		@Override
		public void onDiscovery(final com.epson.epos2.discovery.DeviceInfo deviceInfo) {
			// TODO Auto-generated method stub
            setStatus("onDiscovery deviceInfo "+deviceInfo.getTarget());
			   runOnUiThread(new Runnable() {
	                @Override
	                public synchronized void run() {
	                    HashMap<String, String> item = new HashMap<String, String>();
	                    item.put("PrinterName", deviceInfo.getDeviceName());
	                    item.put("Target", deviceInfo.getTarget());
	                    mPrinterList.add(item);
	                    mPrinterListAdapter.notifyDataSetChanged();
	                }
	            });
		}
    };


    public void setStatus(String text)
    {
        final String msg=text;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                status.setText(status.getText()+"\n"+msg);
            }
        });

    }
}
