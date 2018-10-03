package com.example.sunidhi.inclass04;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sunidhi.inclass04.MainActivity.regionName;

/**
 * Created by Sunidhi on 01-Oct-18.
 */

public class discountAdapter extends ArrayAdapter<Results.ResultsValue> {
    APIInterface apiService = APIClient.getClient().create(APIInterface.class);
    public discountAdapter(@NonNull Context context, int resource, @NonNull List<Results.ResultsValue> objects) {
        super( context, resource, objects );
    }
//    String regionName;

//    public discountAdapter(@NonNull ArrayList<String> context, int resource, @NonNull List<Results.ResultsValue> objects) {
////        regionName = this.regionName;
//        super( context, resource, objects );
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Results.ResultsValue resultsValue = getItem( position );
        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from( getContext() ).inflate( R.layout.product_info, parent, false );
            viewHolder = new ViewHolder();
                viewHolder.productName = convertView.findViewById( R.id.textViewProductName );
                viewHolder.discount = convertView.findViewById( R.id.textViewDiscount );
                viewHolder.price = convertView.findViewById( R.id.textViewPrice );
                viewHolder.region = convertView.findViewById( R.id.textViewRegion );
                viewHolder.imageView = convertView.findViewById( R.id.imageView );
                convertView.setTag( viewHolder );

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.productName.setText( resultsValue.getName() );
        viewHolder.discount.setText( resultsValue.getDiscount() );
        viewHolder.price.setText( resultsValue.getPrice() );
        viewHolder.region.setText( resultsValue.getRegion() );

        String url = "http://13.58.41.200:3000";

        Picasso.with(getContext()).load(url+ "/"+resultsValue.getPhoto()).into(viewHolder.imageView);

        return convertView;
    }

    private static class ViewHolder{
        TextView productName, discount, price, region;
        ImageView imageView;
    }
}
