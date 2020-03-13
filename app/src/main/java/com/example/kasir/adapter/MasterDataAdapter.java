package com.example.kasir.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kasir.ConfigDB;
import com.example.kasir.R;
import com.example.kasir.activity.FormMasterDataActivity;
import com.example.kasir.model.MasterDataModel;

import java.util.ArrayList;
import java.util.List;

public class MasterDataAdapter extends RecyclerView.Adapter<MasterDataAdapter.ViewHolder> {

    private Context context;
    private List<MasterDataModel> masterDataModels = new ArrayList<>();

    public MasterDataAdapter(Context context) {
        this.context = context;
    }

    //ini nanti isi dari listnya
    public void setMasterDataModels(List<MasterDataModel> masterDataModelsParam) {

        this.masterDataModels = masterDataModelsParam;
    }

    //untuk set layout kamu pertama kali
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_master_data, parent, false);
        return new ViewHolder(view);
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        //untuk set gambar, text...
        //position = dari 0 sampai jumlah listnya, ini nanti looping dari 0 sampai jumlah listnya
        final MasterDataModel dataModel = masterDataModels.get(position);


        Log.e("TAG_PHOTO", ConfigDB.URL_PHOTO+dataModel.getProduk_gambar());

        Glide.with(context)
                .load(ConfigDB.URL_PHOTO+dataModel.getProduk_gambar())
                .placeholder(R.drawable.ic_add)
                .into(viewHolder.imageView);

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtDesc.setText(dataModel.getDesc());

        viewHolder.btnRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormMasterDataActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idProduk",dataModel.getIdProduk());
                intent.putExtra("kat_id",dataModel.getKatId());
                intent.putExtra("namaProduk",dataModel.getNamaProduk());
                intent.putExtra("kodeProduk",dataModel.getKode());
                intent.putExtra("gambarProduk",dataModel.getProduk_gambar());
                intent.putExtra("hargaJual",dataModel.getHargaJual());
                intent.putExtra("pcs",dataModel.getPcs());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //ini nanti jumlah list kamu, sesuaikan jumlah listnya
        return masterDataModels.size();
    }


}
