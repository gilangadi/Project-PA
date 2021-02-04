package com.example.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.Api.Api;
import com.example.myapp.Api.InterfaceConnection;
import com.example.myapp.Model.ApiConnection;
import com.example.myapp.Model.Data_Response;
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

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputBukuJurnalAdminFragment extends Fragment {

    TextInputEditText textInputEditTextJudul, textInputEditTextPengarang, textInputEditTextTahunTerbit, editTextNamaPdf;
    AutoCompleteTextView textInputLayoutspin, textInputLayoutspin1, textInputEditTextStatus, textInputEditTextStatusbukujurnal;
    AppCompatButton buttonpdf, buttonimage, buttonCreate;
    ImageView imageViewcreate;
    String encodeImageString;
    Bitmap bitmap;
    private String  judul, pengarang, tahun_terbit, status_buku_jurnal, status;
    Uri imageUri;

    InterfaceConnection interfaceConnection;

    private int REQ_PDF = 21;
    private String encodePDF;

    private String pdfName;
    Uri filePathPdf;

    private AutoCompleteTextView dropdown, dropdown1;
    final int CODE_GALLERY_REQUEST = 999;
    private final String URLs = "http://192.168.43.183/apiperpus/public/api/rak";


    public static InputBukuJurnalAdminFragment newInstance(String param1, String param2) {
        InputBukuJurnalAdminFragment fragment = new InputBukuJurnalAdminFragment();
        return fragment;
    }

    public InputBukuJurnalAdminFragment() {
        // Required empty public constructor
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

        //pilih pdf
        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("aplication/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select pdf Files"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        }).check();
            }
        });

        //Button tambah data buku/jurnal
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(imageUri != null){
//                    upload(imageUri,
//
//                                if (judul.trim().equals("")){
//                                    textInputEditTextJudul.setError("Judul harus di isi");
////                                    textInputEditTextJudul.requestFocus();
//                                }
//                                else if (pengarang.trim().equals("")){
//                                    textInputEditTextPengarang.setError("pengarang harus di isi");
////                                    textInputEditTextPengarang.requestFocus();
//                                }
//                                else if (tahun_terbit.trim().equals("")){
//                                    textInputEditTextTahunTerbit.setError("tahun terbit harus di isi");
////                                    textInputEditTextTahunTerbit.requestFocus();
//                                }
//                                else if (status_buku_jurnal.trim().equals("")){
//                                    textInputEditTextStatusbukujurnal.setError("status buku/jurnal harus di isi");
//                                    textInputEditTextStatusbukujurnal.requestFocus();
//                                }
//                                else if (status.trim().equals("")){
//                                    textInputEditTextStatus.setError("status harus di isi");
//                                    textInputEditTextStatus.requestFocus();
//                                }
                                upload();
//                            textInputEditTextJudul.getText().toString(),
//                            textInputEditTextPengarang.getText().toString(),
//                            textInputEditTextTahunTerbit.getText().toString(),
//                            textInputEditTextStatus.getText().toString(),
//                            textInputEditTextStatusbukujurnal.getText().toString());


//                }
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
//            finish();
            startActivity(intent);
        }
        return view;
    }

    private void upload() {

        String judul = textInputEditTextJudul.getText().toString();
        String pengarang = textInputEditTextPengarang.getText().toString();
        String tahun_terbit = textInputEditTextTahunTerbit.getText().toString();
        String status_buku_jurnal = textInputEditTextStatusbukujurnal.getText().toString();
        String status = textInputEditTextStatus.getText().toString();

        interfaceConnection = ApiConnection.getClient().create(InterfaceConnection.class);

        if (judul.isEmpty()){
            textInputEditTextJudul.setError("isi");
            textInputEditTextJudul.requestFocus();
            return;
        }
        if (pengarang.isEmpty()){
            textInputEditTextPengarang.setError("isi");
            textInputEditTextPengarang.requestFocus();
            return;
        }
        if (tahun_terbit.isEmpty()){
            textInputEditTextTahunTerbit.setError("isi");
            textInputEditTextTahunTerbit.requestFocus();
            return;
        }
        if (status_buku_jurnal.isEmpty()){
            textInputEditTextStatusbukujurnal.setError("isi");
            textInputEditTextStatusbukujurnal.requestFocus();
            return;
        }
        if (status.isEmpty()){
            textInputEditTextStatus.setError("isi");
            textInputEditTextStatus.requestFocus();
            return;
        }

        //        VALIDASI
//        if (textInputEditTextJudul.getText().toString().length() == 0) {
//            textInputEditTextJudul.setError("Silakan masukkan judul");
//            textInputEditTextJudul.requestFocus();
//            return;
//        }
//        if (textInputEditTextPengarang.getText().toString().length() == 0) {
//            textInputEditTextPengarang.setError("Silakan masukkan pengarang");
//            textInputEditTextPengarang.requestFocus();
//            return;
//        }
//        if (textInputEditTextTahunTerbit.getText().toString().length() == 0) {
//            textInputEditTextTahunTerbit.setError("Silakan masukkan tahun terbit");
//            textInputEditTextTahunTerbit.requestFocus();
//            return;
//        }
//        if (textInputEditTextStatus.getText().toString().length() == 0) {
//            textInputEditTextStatus.setError("Silakan masukkan status");
//            textInputEditTextStatus.requestFocus();
//            return;
//        }
//        if (textInputEditTextStatusbukujurnal.getText().toString().length() == 0) {
//            textInputEditTextStatusbukujurnal.setError("Silakan masukkan status buku/jurnal");
//            textInputEditTextStatusbukujurnal.requestFocus();
//            return;
//        }


//        File file = new File(getRealPathFromURI(imageUri));
//        Toast.makeText(getContext(), (int) file.length(), Toast.LENGTH_SHORT).show();
//        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(imageUri)), file);
//        RequestBody requestJudul = RequestBody.create(MediaType.parse("text/plain"), judul);
//        RequestBody requestPengarang = RequestBody.create(MediaType.parse("text/plain"), pengarang);
//        RequestBody requestTahun_terbit = RequestBody.create(MediaType.parse("text/plain"), tahun_terbit);
//        RequestBody requestStatus = RequestBody.create(MediaType.parse("text/plain"), status);
//        RequestBody requestStatus_buku_jurnal = RequestBody.create(MediaType.parse("text/plain"), status_buku_jurnal);
//
//        Gson gson = new GsonBuilder().setLenient().create();
////
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
////
//        Api api = retrofit.create(Api.class);
//        Call<ResponseModel> call = api.upload(requestJudul, requestPengarang, requestTahun_terbit, requestStatus, requestStatus_buku_jurnal);
////        Call<ResponseModel> call = Retrofit
//        call.enqueue(new Callback<ResponseModel>()

        Call<Data_Response> upload = interfaceConnection.create(judul,pengarang,tahun_terbit,status_buku_jurnal,status);
        upload.enqueue(new Callback<Data_Response>() {
            @Override
            public void onResponse(Call<Data_Response> call, Response<Data_Response> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Data_Response> call, Throwable t) {
                Log.d("Error here", "Error here", t);
                t.printStackTrace();
                Log.d("Error here", "Error here2", t);
                Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();

            }
        });
//        {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                ResponseModel obj = response.body();
//                if (obj.isError()){
//
//                }else {
//
//                }
//                Toast.makeText(getContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
//
//                if (!response.body().isError()) {
//                    Toast.makeText(getContext(), "Sukses Upload", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getContext(), "Gagal Upload", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && requestCode == RESULT_OK && data != null) {
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
