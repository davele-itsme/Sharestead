package dk.via.sharestead.view;

import android.Manifest;
import android.app.AlertDialog;
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

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import dk.via.sharestead.R;
import dk.via.sharestead.view.dialog.ProgressDialog;
import dk.via.sharestead.viewmodel.ProfileViewModel;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private static final String TAG = "Progress dialog";
    private ProgressDialog progressDialog;
    private TextView nameText;
    private TextView emailText;
    private CircleImageView imageView;
    private TextView help;
    private TextView privacyPolicy;
    private static final int STORAGE_REQUEST_CODE = 100;
    private static final int STORAGE_PICK_REQUEST_CODE = 200;
    private String[] storagePermissions;
    private Uri uri;
    private ProfileViewModel profileViewModel;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDialog();
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setViews(view);
        setText();
        setOnClickListeners();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorageAccepter = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (writeStorageAccepter) {
                    pickFromGallery();
                } else {
                    Toast.makeText(getContext(), "Please allow storage permissions", Toast.LENGTH_SHORT).show();
                }
            }
        }
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

    private void setViews(View view) {
        nameText = view.findViewById(R.id.profileName);
        emailText = view.findViewById(R.id.profileEmail);
        imageView = view.findViewById(R.id.profileImage);
        help = view.findViewById(R.id.help);
        privacyPolicy = view.findViewById(R.id.privacyPolicy);
    }

    private void setText() {
        profileViewModel.setText();
        profileViewModel.getTextLiveData().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                if (!strings[0].equals("")) {
                    nameText.setText(strings[0]);
                }
                if (!strings[1].equals("")) {
                    Picasso.with(getContext()).load(strings[1]).resize(400, 400).centerCrop().into(imageView);
                }
                if (!strings[2].equals("")) {
                    emailText.setText(strings[2]);
                }
                progressDialog.dismiss();
            }
        });
    }

    private void setDialog() {
        progressDialog = new ProgressDialog();
        progressDialog.show(getActivity().getSupportFragmentManager(), TAG);
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

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrivacyPolicyDialog();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpDialog();
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

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, STORAGE_PICK_REQUEST_CODE);
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
                .setPositiveButton("Change", (dialog, which) -> {
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
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void showPrivacyPolicyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.privacy_policy));
        builder.setMessage(getResources().getString(R.string.privacy_policy_message));

        builder.setPositiveButton(getResources().getString(R.string.okay), (dialogInterface, i) -> {
            String url = "https://sharestead.flycricket.io/privacy.html";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        builder.setNeutralButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.help_and_support));
        builder.setMessage(getResources().getString(R.string.function_not_implemented));

        builder.setPositiveButton(getResources().getString(R.string.understand), (dialogInterface, i) -> {
                dialogInterface.dismiss();
        });
        builder.setNeutralButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
