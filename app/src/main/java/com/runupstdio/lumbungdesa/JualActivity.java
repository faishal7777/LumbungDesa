package com.runupstdio.lumbungdesa;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.AddProduct;
import com.runupstdio.lumbungdesa.Model.UploadImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JualActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mSpinner;
    int idCategory;
    EditText mSellProductName, mSellProductPrice, mSellProductDesc, mSellExpAt, mSellStock;
    Button mSellAddPict, mSellButton;
    RoundedImageView mPic1, mPic2, mPic3;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    String[] mediaPath = new String[3];

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mSpinner = findViewById(R.id.spinner_Kategori);
        mSellProductName = findViewById(R.id.sell_productName);
        mSellProductPrice = findViewById(R.id.sell_productPrice);
        mSellProductDesc = findViewById(R.id.sell_productDesc);
        mSellExpAt = findViewById(R.id.sell_expAt);
        mSellStock = findViewById(R.id.sell_stock);
        mSellAddPict = findViewById(R.id.sell_addPict);
        mSellButton = findViewById(R.id.sell_button);
        mPic1 = findViewById(R.id.sell_pic1);
        mPic2 = findViewById(R.id.sell_pic2);
        mPic3 = findViewById(R.id.sell_pic3);
        mSpinner.setOnItemSelectedListener(this);

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
            Intent intent = new Intent(JualActivity.this, NavigationBar.class);
            startActivity(intent);
        }

        // Spinner Drop down elements
        List<String> kategori = new ArrayList<>();
        kategori.add("Beras dan Biji");
        kategori.add("Buah");
        kategori.add("Daging");
        kategori.add("Olahan");
        kategori.add("Sayur");
        kategori.add("Telur");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategori);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);

        mSellAddPict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(JualActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(JualActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_product();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        idCategory = position+1;
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                if(mediaPath[0] == null) {
                    mediaPath[0] = cursor.getString(columnIndex);
                    Glide.with(JualActivity.this)
                            .asBitmap()
                            .load(mediaPath[0])
                            .into(mPic1);
                    mPic1.setVisibility(View.VISIBLE);
                } else if(mediaPath[1] == null) {
                    mediaPath[1] = cursor.getString(columnIndex);
                    Glide.with(JualActivity.this)
                            .asBitmap()
                            .load(mediaPath[1])
                            .into(mPic2);
                    mPic2.setVisibility(View.VISIBLE);
                } else if(mediaPath[2] == null) {
                    mediaPath[2] = cursor.getString(columnIndex);
                    Glide.with(JualActivity.this)
                            .asBitmap()
                            .load(mediaPath[2])
                            .into(mPic3);
                    mPic3.setVisibility(View.VISIBLE);
                    mSellAddPict.setVisibility(View.GONE);
                }

                // Set the Image in ImageView for Previewing the Media
                //imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
            }
        } catch (Exception e) {
            Toast.makeText(JualActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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

    public void add_product(){
        String[] newMediaPath = removeNulls(mediaPath);

        MultipartBody.Part[] product_image = new MultipartBody.Part[newMediaPath.length];

        for (int index = 0; index < newMediaPath.length; index++) {
            if(newMediaPath[index] != null) {
                Log.d("UPLOAD", "requestUploadSurvey: "+newMediaPath.length+"survey image " + index + "  " + newMediaPath[index]);
                File file = new File(newMediaPath[index]);

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                product_image[index] = MultipartBody.Part.createFormData("product_image[]", file.getName(), requestBody);
            }
        }

        RequestBody szProductName = RequestBody.create(MediaType.parse("text/plain"), mSellProductName.getText().toString());
        RequestBody szProductDesc = RequestBody.create(MediaType.parse("text/plain"), mSellProductDesc.getText().toString());

        Call<AddProduct> call = mApiClient.add_product("Bearer "+idToken,
                szProductName,
                szProductDesc,
                Integer.parseInt(mSellProductPrice.getText().toString()),
                idCategory,
                Integer.parseInt(mSellExpAt.getText().toString()),
                product_image);

        call.enqueue(new Callback<AddProduct>() {
            @Override
            public void onResponse(Call<AddProduct> call, Response<AddProduct> response)
            {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toast.makeText(JualActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent productDetails = new Intent(JualActivity.this, ProductClickedActivity.class);
                        productDetails.putExtra("prodId", response.body().getData().getProduct().getId());
                        startActivity(productDetails);
                    } else {
                        Toast.makeText(JualActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(JualActivity.this, errorBody, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddProduct> call, Throwable t) {
                Toast.makeText(JualActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    String[] removeNulls(String[] nullsArray) {
        int countNulls = 0;

        for (int i = 0; i < nullsArray.length; i++) { // count nulls in array
            if (nullsArray[i] == null) {
                countNulls++;
            }
        }
        // creating new array with new length (length of first array - counted nulls)
        String[] nullsRemoved = new String[nullsArray.length - countNulls];

        for (int i = 0, j = 0; i < nullsArray.length; i++) {

            if (nullsArray[i] != null) {
                nullsRemoved[j] = nullsArray[i];
                j++;
            }
        }
        return nullsRemoved;
    }
}
