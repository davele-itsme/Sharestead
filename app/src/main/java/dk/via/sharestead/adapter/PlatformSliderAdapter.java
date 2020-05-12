package dk.via.sharestead.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import dk.via.sharestead.model.SliderItem;
import dk.via.sharestead.R;

public class PlatformSliderAdapter extends RecyclerView.Adapter<PlatformSliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItems;
    private OnPlatformItemClickListener clickListener;

    public PlatformSliderAdapter(List<SliderItem> sliderItems, OnPlatformItemClickListener clickListener) {
        this.sliderItems = sliderItems;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem item = sliderItems.get(position);
        if (item.isSelected()) {
            holder.platformText.setTextColor(Color.parseColor("#0B9DDB"));
        } else {
            holder.platformText.setTextColor(Color.parseColor("#000000"));
        }
        holder.platformText.setText(item.getPlatformText());
        holder.setImageView(sliderItems.get(position));
    }


    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView platformText;
        private RoundedImageView image;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            platformText = itemView.findViewById(R.id.platformText);
            image = itemView.findViewById(R.id.imageSlide);
            itemView.setOnClickListener(this);
        }

        void setImageView(SliderItem sliderItem) {
            image.setImageResource(sliderItem.getImage());
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClickListener(getAdapterPosition());
            v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_scale));
            notifyItemChanged(getAdapterPosition());
        }
    }

    public interface OnPlatformItemClickListener {
        void onItemClickListener(int clickedItemIndex);
    }

}
