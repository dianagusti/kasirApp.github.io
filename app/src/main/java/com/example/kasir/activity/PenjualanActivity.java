package com.example.kasir.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kasir.R;
import com.example.kasir.app.App;
import com.example.kasir.model.MasterDataModel;

import java.util.List;

public class PenjualanActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LinearLayout btnNext;
    private RecyclerView recyclerView;
    List<MasterDataModel> listMasterData;
    private MasterDataAdapter masterDataAdapter;
    private TextView txtTotalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Penjualan");
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PenjualanActivity.this, PaymentCashActivity.class);
                startActivity(i);
            }
        });

        txtTotalHarga = (TextView)findViewById(R.id.txtTotalHarga);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        masterDataAdapter = new MasterDataAdapter(getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(masterDataAdapter);


        listMasterData = App.getInstance().listMasterData;

        txtTotalHarga.setText(String.valueOf(App.getInstance().intTotal));

        masterDataAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home: {

                finish();

                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }

    public class MasterDataAdapter extends RecyclerView.Adapter<MasterDataAdapter.ViewHolder> {

        private Context context;
//        private List<MasterDataModel> masterDataModels = new ArrayList<>();

        public MasterDataAdapter(Context context) {
            this.context = context;
        }


        //untuk set layout kamu pertama kali
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_transaksi_detail__data, parent, false);
            return new MasterDataAdapter.ViewHolder(view);
        }


        //untuk mengatur cardview kamu nanti
        public class ViewHolder extends RecyclerView.ViewHolder  {

            private ImageView imageView;
            private TextView txtTitle, txtDesc;
            private LinearLayout btnRow;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                btnRow = itemView.findViewById(R.id.btnRow);
                imageView = itemView.findViewById(R.id.imgIcon);
                txtTitle = itemView.findViewById(R.id.txtTitle);
                txtDesc = itemView.findViewById(R.id.txtDesc);

            }


        }

        @Override
        public void onBindViewHolder(@NonNull MasterDataAdapter.ViewHolder viewHolder, int position) {

            //untuk set gambar, text...
            //position = dari 0 sampai jumlah listnya, ini nanti looping dari 0 sampai jumlah listnya
            final MasterDataModel dataModel = listMasterData.get(position);

            viewHolder.imageView.setImageResource(dataModel.getImgResource());
            viewHolder.txtTitle.setText(dataModel.getTitle());
            viewHolder.txtDesc.setText(dataModel.getDesc()+" x "+ String.valueOf(dataModel.getTotal()));

        }

        @Override
        public int getItemCount() {
            //ini nanti jumlah list kamu, sesuaikan jumlah listnya
            return listMasterData.size();
        }


    }
}
