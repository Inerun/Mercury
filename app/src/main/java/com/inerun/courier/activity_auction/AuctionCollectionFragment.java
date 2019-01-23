package com.inerun.courier.activity_auction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.base.AuctionBaseFragment;
import com.inerun.courier.model.AuctionInvoice;
import com.inerun.courier.model.AuctionInvoice_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by vineet on 28/05/18.
 */

public class AuctionCollectionFragment extends AuctionBaseFragment {

    private TextView total_collection, cash_val, card_val, cheque_val, bank_transfer_val;
    private List<AuctionInvoice> auctionInvoiceList;

    public static AuctionCollectionFragment newInstance() {

        Bundle args = new Bundle();

        AuctionCollectionFragment fragment = new AuctionCollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        setShowBackArrow(true);
        return R.layout.auction_collection_fragment;
    }

    @Override
    protected void initView(View rootview) throws IonServiceManager.InvalidParametersException {
        setActionBarTitle(getString(R.string.collection_title));
        total_collection = rootview.findViewById(R.id.collection_value);
        cash_val = rootview.findViewById(R.id.cash_value);
        card_val = rootview.findViewById(R.id.card_value);
        cheque_val = rootview.findViewById(R.id.cheque_value);
        bank_transfer_val = rootview.findViewById(R.id.bank_transfer_value);
        getData();

    }

    private void getData() {
        auctionInvoiceList = SQLite.select().from(AuctionInvoice.class).where(AuctionInvoice_Table.sync_status.eq(AuctionInvoice.SYNC_STATUS_PENDING)).and(AuctionInvoice_Table.status.eq(1)).queryList();
        setData(auctionInvoiceList);
    }


    private void setData(List<AuctionInvoice> auctionInvoiceList) {

        float total = 0;
        float cash = 0;
        float card = 0;
        float returnAmount = 0;
        float cheque = 0;
        float bankTransfer = 0;
        if (auctionInvoiceList != null && auctionInvoiceList.size() > 0) {
            for (AuctionInvoice auctionInvoice : auctionInvoiceList) {
                total += auctionInvoice.getGrand_total();
                cash += auctionInvoice.getCash_amount();
                returnAmount += auctionInvoice.getAmount_returned();
                card += auctionInvoice.getCard_amount();
                cheque += auctionInvoice.getCheque_amount();
                bankTransfer += auctionInvoice.getBank_transfer();
            }
        }
        cash = cash - returnAmount;
        total_collection.setText(getString(R.string.auction_collection_val, "" + total));
        cash_val.setText("" + cash);
        card_val.setText("" + card);
        cheque_val.setText("" + cheque);
        bank_transfer_val.setText("" + bankTransfer);


    }
}
