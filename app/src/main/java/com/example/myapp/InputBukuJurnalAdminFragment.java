package com.example.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.example.myapp.Api.Api;
import com.example.myapp.Model.MyResponse;
import com.example.myapp.Model.ResponseModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class InputBukuJurnalAdminFragment extends Fragment {

    TextInputEditText textInputEditTextJudul, textInputEditTextPengarang, textInputEditTextTahunTerbit, editTextNamaPdf;
    AutoCompleteTextView textInputLayoutspin, textInputLayoutspin1, textInputEditTextStatus, textInputEditTextStatusbukujurnal;
    AppCompatButton buttonpdf, buttonimage, buttonCreate;
    ImageView imageViewcreate;
    private AutoCompleteTextView dropdown, dropdown1;
    Uri imageUri;

    public InputBukuJurnalAdminFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_input_buku_jurnal_admin, container, false);

        textInputEditTextJudul = (TextInputEditText) view.findViewById(R.id.judul_create);
        textInputEditTextPengarang = (TextInputEditText) view.findViewById(R.id.pengarang_create);
        textInputEditTextTahunTerbit = (TextInputEditText) view.findViewById(R.id.tahun_terbit_create);
        textInputEditTextStatus = (AutoCompleteTextView) view.findViewById(R.id.status_create);
        textInputEditTextStatusbukujurnal = (AutoCompleteTextView) view.findViewById(R.id.status_create_buku_jurnal);
        editTextNamaPdf = (TextInputEditText) view.findViewById(R.id.nama_pdf);
        buttonCreate = (AppCompatButton) view.findViewById(R.id.button_tambah_data_admin);
        buttonimage = (AppCompatButton) view.findViewById(R.id.button_image_create_admin);
        buttonpdf = (AppCompatButton) view.findViewById(R.id.button_pdf_create_admin);
        imageViewcreate = (ImageView) view.findViewById(R.id.image_create);

        textInputLayoutspin = view.findViewById(R.id.status_create);
        textInputLayoutspin1 = view.findViewById(R.id.status_create_buku_jurnal);

        dropdown = view.findViewById(R.id.status_create);
        dropdown1 = view.findViewById(R.id.status_create_buku_jurnal);

        //pilih image
        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        //Buttom create
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    upload(imageUri,
                            textInputEditTextJudul.getText().toString(),
                            textInputEditTextPengarang.getText().toString(),
                            textInputEditTextTahunTerbit.getText().toString(),
                            textInputEditTextStatusbukujurnal.getText().toString(),
                            textInputEditTextStatus.getText().toString());
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);

        }
        return view;
    }

    private void upload(Uri fileUri, String judul, String pengarang, String tahun_terbit, String status_buku_jurnal, String status) {

        File file = new File(getRealPathFromURI(fileUri));
        Toast.makeText(requireActivity().getApplicationContext(),""+ file.length(), Toast.LENGTH_SHORT).show();
//        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);
        RequestBody requestJudul = RequestBody.create(MediaType.parse("text/plain"), judul);
        RequestBody requestPengarang = RequestBody.create(MediaType.parse("text/plain"), pengarang);
        RequestBody requestTahun_terbit = RequestBody.create(MediaType.parse("text/plain"), tahun_terbit);
        RequestBody requestStatus_buku_jurnal = RequestBody.create(MediaType.parse("text/plain"), status_buku_jurnal);
        RequestBody requestStatus = RequestBody.create(MediaType.parse("text/plain"), status);


        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);
        Call<MyResponse> call = api.upload( requestJudul, requestPengarang, requestTahun_terbit, requestStatus_buku_jurnal, requestStatus);
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.body().isError()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Sukses Upload", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireActivity().getApplicationContext(), "Gagal Upload", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int columIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(columIndex);
        cursor.close();
        return result;
    }
}
