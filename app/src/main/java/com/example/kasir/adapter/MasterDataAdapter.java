package com.example.kasir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasir.R;
import com.example.kasir.model.MasterDataModel;
import com.example.kasir.utils.UtilsDialog;

import java.util.ArrayList;
import java.util.List;

public class MasterDataAdapter extends RecyclerView.Adapter<MasterDataAdapter.ViewHolder> {

    private Context context;
    private List<MasterDataModel> masterDataModels = new ArrayList<>();

    public MasterDataAdapter(Context context) {
        this.context = context;
    }

    //ini nanti isi dari listnya
    public void setMasterDataModels(List<MasterDataModel> masterDataModels) {
        this.masterDataModels = masterDataModels;
    }

    //untuk set layout kamu pertama kali
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_master_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        //untuk set gambar, text...
        //position = dari 0 sampai jumlah listnya, ini nanti looping dari 0 sampai jumlah listnya
        MasterDataModel dataModel = masterDataModels.get(position);

        viewHolder.imageView.setImageResource(dataModel.getImgResource());
        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtDesc.setText(dataModel.getDesc());
    }

    @Override
    public int getItemCount() {
        //ini nanti jumlah list kamu, sesuaikan jumlah listnya
        return masterDataModels.size();
    }

    //untuk mengatur cardview kamu nanti
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView txtTitle, txtDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            UtilsDialog.showToast(context, "Clicked position : " + getAdapterPosition());
        }
    }
}
