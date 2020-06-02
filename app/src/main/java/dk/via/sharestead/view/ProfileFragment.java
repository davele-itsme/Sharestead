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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import dk.via.sharestead.viewmodel.ProfileViewModel;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = "Progress dialog";
    private ProgressDialog progressDialog;
    TextView nameText;
    TextView emailText;
    CircleImageView imageView;
    private static final int STORAGE_REQUEST_CODE = 100;
    private static final int STORAGE_PICK_REQUEST_CODE = 200;
    private String[] storagePermissions;
    private Uri uri;
    private ProfileViewModel profileViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setDialog();

        //required for checking permissions
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        return inflater.inflate(R.layout.profile_fragment, container, false);
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
        profileViewModel.setText();
        nameText = view.findViewById(R.id.profileName);
        emailText = view.findViewById(R.id.profileEmail);
        imageView = view.findViewById(R.id.profileImage);
        profileViewModel.getTextLiveData().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                if (!strings[0].equals("")) {
                    nameText.setText(strings[0]);
                }
                if (!strings[1].equals("")) {
                    Picasso.with(getContext()).load(strings[1]).into(imageView);
                }
                if (!strings[2].equals("")) {
                    emailText.setText(strings[2]);
                }
                progressDialog.dismiss();
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
        profileViewModel.uploadImageWithUri(uri);
        profileViewModel.getUpdateImage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("success")) {
                    Picasso.with(getContext()).load(uri).into(imageView);
                    Toast.makeText(getContext(), "Image updated", Toast.LENGTH_SHORT).show();
                } else if (s.equals("error update")) {
                    Toast.makeText(getContext(), "Error with updating image occurred", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    private void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change name");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.name_dialog, null);
        EditText nameField = view.findViewById(R.id.nameEditText);
        builder.setView(view)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameField.getText().toString();
                        if (!TextUtils.isEmpty(name)) {
                            progressDialog.show(getActivity().getSupportFragmentManager(), TAG);
                            profileViewModel.changeName(name);
                            profileViewModel.getUpdateName().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {
                                    if (aBoolean) {
                                        nameText.setText(name);
                                        Toast.makeText(getContext(), "Name successfully updated", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Error with updating name", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                        } else {
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
