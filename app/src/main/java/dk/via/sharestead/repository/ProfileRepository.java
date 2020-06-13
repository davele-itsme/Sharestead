package dk.via.sharestead.repository;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileRepository {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private MutableLiveData<String[]> textLiveData = new MutableLiveData<>();
    private MutableLiveData<String> updateImage = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateName = new MutableLiveData<>();

    private static final String STORAGE_PATH = "Users_Profile_Image";
    private static ProfileRepository instance;
    private Application application;

    public ProfileRepository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public static ProfileRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ProfileRepository(application);
        }
        return instance;
    }

    public void setText() {
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = "";
                String image = "";
                String email = user.getEmail();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    name = "" + ds.child("name").getValue();
                    image = "" + ds.child("image").getValue();
                }
                String[] textArray = new String[]{name, image, email};
                textLiveData.setValue(textArray);
                textLiveData = new MutableLiveData<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<String[]> getTextLiveData() {
        return textLiveData;
    }

    public void uploadImageWithUri(Uri uri) {
        String filePath = STORAGE_PATH + "" + user.getUid();
        StorageReference storageReference1 = storageReference.child(filePath);
        storageReference1.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful()) ;
            Uri downloadUri = uriTask.getResult();

            //Check if image is uploaded
            if (uriTask.isSuccessful()) {
                //update url in user database
                HashMap<String, Object> results = new HashMap<>();
                results.put("image", downloadUri.toString());
                databaseReference.child(user.getUid()).updateChildren(results).addOnSuccessListener(aVoid -> {
                    updateImage.setValue("success");
                    updateImage = new MutableLiveData<>();
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateImage.setValue("error update");
                        updateImage = new MutableLiveData<>();
                    }
                });
            } else {
                updateImage.setValue("error");
                updateImage = new MutableLiveData<>();
            }
        });
    }

    public LiveData<String> getUpdateImage() {
        return updateImage;
    }

    public void changeName(HashMap<String, Object> hashMap) {
        databaseReference.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateName.setValue(true);
            } else {
                updateName.setValue(false);
            }
            updateName = new MutableLiveData<>();
        });
    }

    public LiveData<Boolean> getUpdateName() {
        return updateName;
    }
}
