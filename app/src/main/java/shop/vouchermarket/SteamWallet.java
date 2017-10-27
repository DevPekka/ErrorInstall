package shop.vouchermarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.tapadoo.alerter.Alerter;

public class SteamWallet extends AppCompatActivity implements BillingProcessor.IBillingHandler{
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steam_wallet);

        bp = new BillingProcessor(this, null, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Steam Wallet");

        configureIt();
    }

    public void configureIt(){
        LinearLayout ln1 = (LinearLayout) findViewById(R.id.s12k);
        ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alerter.create(SteamWallet.this)
                        .setTitle("Still Working")
                        .setText("Our Purchases is being Worked." +
                                "   Approximation will done on 22 Desember 2017.")
                        .show()
                        .enableSwipeToDismiss();
            }
        });

        LinearLayout ln2 = (LinearLayout) findViewById(R.id.s45k);
        ln2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(SteamWallet.this, "android.test.purchased");
            }
        });
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Alerter.create(SteamWallet.this)
                .setTitle("Purchase Completed!")
                .setText("Thank you very much" +
                        "for purchasing our product! you product soon will send via E-Mail.")
                .show()
                .enableSwipeToDismiss();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Alerter.create(this)
                .setTitle("Purchase InComplete!")
                .setText("Sorry, but it's look like, you not completed the Payment.")
                .setBackgroundColorRes(R.color.colorError)
                .show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (!bp.handleActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
