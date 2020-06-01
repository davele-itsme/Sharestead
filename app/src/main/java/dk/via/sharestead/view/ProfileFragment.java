package dk.via.sharestead.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import de.hdodenhof.circleimageview.CircleImageView;
import dk.via.sharestead.R;
import dk.via.sharestead.view.dialog.ProgressDialog;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = "Progress dialog";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private static final String STORAGE_PATH = "Users_Profile_Image";
    private ProgressDialog progressDialog;
    TextView nameText;
    TextView emailText;
    CircleImageView imageView;
    private static final int STORAGE_REQUEST_CODE = 100;
    private static final int STORAGE_PICK_REQUEST_CODE = 200;
    private String[] storagePermissions;
    private Uri uri;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setFirebase();
        setDialog();
        //required for checking permissions
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    private void setFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private void setDialog() {
        progressDialog = new ProgressDialog();
        progressDialog.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setText(view);
        setOnClickListeners();

    }

    private void setText(View view) {
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        nameText = view.findViewById(R.id.profileName);
        emailText = view.findViewById(R.id.profileEmail);
        imageView = view.findViewById(R.id.profileImage);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                String name = "";
                String image = "";
                String email = user.getEmail();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    name = "" + ds.child("name").getValue();
                    image = "" + ds.child("image").getValue();
                }
                emailText.setText(email);
                if (!name.equals("")) {
                    nameText.setText(name);
                }

                if (!image.equals("")) {
                    Picasso.with(getContext()).load(image).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setOnClickListeners() {
        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialog();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission()) {
                    pickFromGallery();
                } else {
                    requestStoragePermission();
                }
            }
        });
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), storagePermissions, STORAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorageAccepter = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (writeStorageAccepter) {
                    //permissions enabled
                    pickFromGallery();
                } else {
                    //permissions denied
                    Toast.makeText(getContext(), "Please allow storage permissions", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, STORAGE_PICK_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == STORAGE_PICK_REQUEST_CODE && data != null) {
                uri = data.getData();
                uploadImageWithUri(uri);
            }
        }
    }

    private void uploadImageWithUri(Uri uri) {
        progressDialog.show(getActivity().getSupportFragmentManager(), TAG);
        String filePath = STORAGE_PATH + "" + user.getUid();
        StorageReference storageReference1 = storageReference.child(filePath);
        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();

                //Check if image is uploaded
                if (uriTask.isSuccessful()) {
                    //update url in user database
                    HashMap<String, Object> results = new HashMap<>();
                    results.put("image", downloadUri.toString());
                    databaseReference.child(user.getUid()).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Image updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Error with updating image occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change name");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.name_dialog, null);
        EditText nameText = view.findViewById(R.id.nameEditText);
        builder.setView(view)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            String name = nameText.getText().toString().trim();
                            if(!TextUtils.isEmpty(name))
                            {
                                progressDialog.show(getActivity().getSupportFragmentManager(), TAG);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                databaseReference.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getContext(), "Name successfully updated", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Error with updating name", Toast.LENGTH_SHORT).show();
                                        }
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                            }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        builder.create().show();
    }

}
