package dk.via.sharestead.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.model.SliderItem;

public class PreferenceViewModel extends AndroidViewModel {
    private List<SliderItem> sliderItems;

    public PreferenceViewModel(@NonNull Application application) {
        super(application);
        sliderItems = new ArrayList<>();
        setImages();
    }

    private void setImages() {
        sliderItems.add(new SliderItem("COMPUTER", R.drawable.computer));
        sliderItems.add(new SliderItem("CONSOLE", R.drawable.console));
        sliderItems.add(new SliderItem("MOBILE", R.drawable.mobile));
        sliderItems.add(new SliderItem("VIRTUAL REALITY", R.drawable.virtual_reality));
    }

    public List<SliderItem> getSliderItems() {
        return sliderItems;
    }

    public void selectPlatform(int clickedItemIndex) {
        SliderItem sliderItem = sliderItems.get(clickedItemIndex);
        if(sliderItem.isSelected())
        {
            sliderItem.setSelected(false);
        }
        else {
            sliderItem.setSelected(true);
        }
    }
}
