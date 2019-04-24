package com.runupstdio.lumbungdesa;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Profile;
import com.runupstdio.lumbungdesa.Model.Register;
import com.runupstdio.lumbungdesa.Model.UploadImage;
import com.runupstdio.lumbungdesa.Model.UserExist;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

public class AkunLoginFragment extends Fragment {

    TextView mProfileName, mProfileStatus;
    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    RoundedImageView mProfileAva;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    String mediaPath, mediaPath1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_akun, null);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);
        settings = this.getActivity().getSharedPreferences("RUNUP", getContext().MODE_PRIVATE);

        mProfileName = v.findViewById(R.id.profile_name);
        mProfileStatus = v.findViewById(R.id.profile_status);
        mProfileAva = v.findViewById(R.id.profile_ava);

        settings = getActivity().getSharedPreferences("RUNUP", getActivity().getApplicationContext().MODE_PRIVATE);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {
            Intent intent = new Intent(getActivity(), NavigationBar.class);
            startActivity(intent);
        }

        setUserData();

        Button mJualProduk = v.findViewById(R.id.btnJualProduk);
        mJualProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mJual = new Intent(v.getContext(), JualActivity.class);
                startActivity(mJual);
            }
        });

        LinearLayout mChat = v.findViewById(R.id.listChat);
        mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(v.getContext(), ListChatActivity.class);
                startActivity(chat);
            }
        });

        LinearLayout mKeluar = v.findViewById(R.id.keluar);
        mKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent mJual = new Intent(v.getContext(), NavigationBar.class);
                startActivity(mJual);
            }
        });

        mProfileAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 1);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                //imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                uploadFile();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }


    private void uploadFile() {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("input_img", file.getName(), requestBody);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("imageData", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Log.e("keshav","mediaPath         ->  " +mediaPath);
        Log.e("keshav","file         ->  " +file);
        Log.e("keshav","file.getName ->  " +file.getName());
        Log.e("keshav","requestBody  ->  " +requestBody);
        Log.e("keshav","fileToUpload ->  " +fileToUpload);
        Log.e("keshav","filename     ->  " +filename);

        Call<UploadImage> call = mApiClient.set_profile_ava("Bearer "+idToken, fileToUpload, filename);
//        Call<ServerResponseKeshav> call = getResponse.uploadFile(fileToUpload, filename);

        call.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(Call<UploadImage> call, Response<UploadImage> response)
            {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        editor = settings.edit();
                        editor.putString("ProfileAva", response.body().getData());
                        editor.commit();
                        Glide.with(getContext())
                                .asBitmap()
                                .load(response.body().getData())
                                .into(mProfileAva);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(getActivity(), errorBody, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UploadImage> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUserData()
    {
        int id = settings.getInt("ID", 0);
        mProfileName.setText(settings.getString("ProfileName", "User"));
        mProfileStatus.setText(settings.getString("ProfileStatus", "Unverified"));
        Glide.with(getContext())
                .asBitmap()
                .load(settings.getString("ProfileAva", "https://cdn.pixabay.com/photo/2017/02/23/13/05/profile-2092113_960_720.png"))
                .into(mProfileAva);
    }


}
