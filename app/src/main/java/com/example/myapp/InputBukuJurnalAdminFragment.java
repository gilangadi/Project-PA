package com.example.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.example.myapp.Api.Api;
import com.example.myapp.Model.MyResponse;
import com.example.myapp.Model.ResponseModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    Uri filepath;
    private  int REQ_PDF = 21;

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

        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, REQ_PDF);
            }
        });

        //Buttom create
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUri != null) {
                    if (filepath != null){

                    upload(imageUri,filepath,
                            textInputEditTextJudul.getText().toString(),
                            textInputEditTextPengarang.getText().toString(),
                            textInputEditTextTahunTerbit.getText().toString(),
                            textInputEditTextStatusbukujurnal.getText().toString(),
                            textInputEditTextStatus.getText().toString());

                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Masukkan Gambar",Toast.LENGTH_LONG).show();
                }
                }
            }
        });

        //SPINNER STATUS
        String[] items1 = new String[]{
                "buku",
                "jurnal",
        };

        //SPINNER STATUS_BUKU/JURNAL
        String[] items2 = new String[]{
                "tersedia",
        };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                getContext(), R.layout.support_simple_spinner_dropdown_item,
                items1
        );
        dropdown.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                getContext(), R.layout.support_simple_spinner_dropdown_item,
                items2
        );
        dropdown1.setAdapter(adapter2);

        //ijin akses image
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
        }
        return view;
    }

    private void upload(Uri fileUri, Uri filepath, String judul, String pengarang, String tahun_terbit, String status_buku_jurnal, String status) {

        File file = new File(getRealPathFromURI(fileUri));
        File filepdf = new File(getRealPathFromURI(filepath));


        Toast.makeText(requireActivity().getApplicationContext(),""+ file.length(), Toast.LENGTH_SHORT).show();
        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);

        Toast.makeText(requireActivity().getApplicationContext(),""+ filepdf.length(), Toast.LENGTH_SHORT).show();
        RequestBody requestFilePDF = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), filepdf);

        RequestBody requestJudul = RequestBody.create(MediaType.parse("text/plain"), judul);
        RequestBody requestPengarang = RequestBody.create(MediaType.parse("text/plain"), pengarang);
        RequestBody requestTahun_terbit = RequestBody.create(MediaType.parse("text/plain"), tahun_terbit);
        RequestBody requestStatus_buku_jurnal = RequestBody.create(MediaType.parse("text/plain"), status_buku_jurnal);
        RequestBody requestStatus = RequestBody.create(MediaType.parse("text/plain"), status);

        if (judul.isEmpty()){
            textInputEditTextJudul.setError("Judul tidak boleh kosong");
            textInputEditTextJudul.requestFocus();
            return;
        }
        if (pengarang.isEmpty()){
            textInputEditTextPengarang.setError("Pengarang tidak boleh kosong");
            textInputEditTextPengarang.requestFocus();
            return;
        }
        if (tahun_terbit.isEmpty()){
            textInputEditTextTahunTerbit.setError("Tahun terbit tidak boleh kosong");
            textInputEditTextTahunTerbit.requestFocus();
            return;
        }
        if (status_buku_jurnal.isEmpty()){
            textInputEditTextStatusbukujurnal.setError("Pilih status buku/jurnal");
            textInputEditTextStatusbukujurnal.requestFocus();
            return;
        }
        if (status.isEmpty()){
            textInputEditTextStatus.setError("Pilih status");
            textInputEditTextStatus.requestFocus();
            return;
        }


        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);
        Call<MyResponse> call = api.upload(requestFile, requestFilePDF, requestJudul, requestPengarang, requestTahun_terbit, requestStatus_buku_jurnal, requestStatus);
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

        //pdf
        if(requestCode == REQ_PDF && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {
                InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);

//                filepath = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);

                filepath = data.getData();

                editTextNamaPdf.setText("Document Selected");
                buttonpdf.setText("Change Document");

                Toast.makeText(getActivity().getApplicationContext(), "Document Selected", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
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
