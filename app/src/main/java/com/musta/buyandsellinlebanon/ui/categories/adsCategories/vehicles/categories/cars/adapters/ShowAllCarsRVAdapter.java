//package com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.musta.buyandsellinlebanon.R;
//import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars.models.ShowCarsAds;
//import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
//
//import java.util.List;
//
//public class ShowAllCarsRVAdapter extends RecyclerView.Adapter<ShowAllCarsRVAdapter.MyViewHolder> {
//
//    List<ShowCarsAds> showCarsAds;
//    Context context;
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public ImageView ad_image;
//        public TextView textViewTitle;
//        public TextView textViewPrice;
//        public TextView textViewCreetionDate;
//        public TextView textViewPlace;
//
//        public MyViewHolder(View view) {
//            super(view);
//            ad_image = view.findViewById(R.id.ad_image);
//            textViewTitle = view.findViewById(R.id.title);
//            textViewPrice = view.findViewById(R.id.price);
//            textViewCreetionDate = view.findViewById(R.id.creation_date);
//            textViewPlace = view.findViewById(R.id.place_name);
//        }
//    }
//
//    public ShowAllCarsRVAdapter(List<ShowCarsAds> showCarsAds) {
//        this.showCarsAds = showCarsAds;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        context = parent.getContext();
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.main_ads_item, parent, false);
//        return new MyViewHolder(itemView);
//    }
//
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        ShowCarsAds showCarsAd = showCarsAds.get(position);
//
//
//        Glide.with(context)
//                .load(NetworkHelper.IMAGES_PATH + showCarsAd.getImage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.ad_image);
//
////        Glide.with(holder.itemView.getContext()).
////                load(new File())
////                .diskCacheStrategy(DiskCacheStrategy.ALL)
//////                .dontAnimate()
////                .into(holder.ad_image);
////        Glide.with(holder.itemView.getContext()).
////                load("http://192.168.1.5/buy_and_sell_in_lebanon/web/imagesads/Kdd9ZLPr1fh5EppAbEWB_IB-nvSoueFs.jpeg").
////                into(holder.ad_image);
//
//
//        holder.textViewTitle.setText(showCarsAd.getTitle());
//        String priceUnitStr = "";
//        if (showCarsAd.getPrice_unit().equals("dollar")) {
//            priceUnitStr = context.getString(R.string.dollar);
//        } else {
//            priceUnitStr = context.getString(R.string.lira);
//        }
//        holder.textViewPrice.setText(showCarsAd.getPrice() + priceUnitStr);
//        holder.textViewCreetionDate.setText(showCarsAd.getCreation_date());
//        holder.textViewPlace.setText(showCarsAd.getPlace_name());
//    }
//
//    @Override
//    public int getItemCount() {
//        return showCarsAds.size();
//    }
//
//
//}
