package com.example.smartcare;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
//import java.util.Base64;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class Account extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    LinearLayout buttonEdit, saveButton, cancelButton;
    LinearLayout buttonBelow, avatar;

    LinearLayout passwordInputLine, nameInputLine, genderInputLine, birthdateInputLine, ageInputLine, addressInputLine, numberInputLine;

    ImageView profile;

    EditText username, password, name, birthdate, address, number;

    EditText gender;

    TextView refreshPassword, changeProfile;

    Bitmap bitmap;

    Boolean edit = false;
    String change = "0";

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5;
    ProgressDialog progressDialog6, progressDialog7, progressDialog8, progressDialog9;

    String cancel = "0";

    String usernameResponse, passwordResponse, staffNameResponse, birthdateResponse, ageResponse, genderResponse, addressResponse, numberResponse, profileResponse;

    private static final String[] genderList = new String[]{
            "Male", "Female"
    };

    String currentUser, returnPress, concatinatedReturnPress, copyOfReturnPress, copyOfReturnPress1;

    final int CODE_GALLERY_REQUEST = 999;

    String passwordError="0";

    private String ipAddress = "192.168.254.114:8080";

    @DrawableRes
    private int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        setTitle("Profile");

        setUpToolbar();

        buttonEdit = findViewById(R.id.buttonEdit);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        number = findViewById(R.id.number);

        number.setInputType(InputType.TYPE_CLASS_NUMBER);


        passwordInputLine = findViewById(R.id.passwordInputLine);
        nameInputLine = findViewById(R.id.nameInputLine);
        genderInputLine = findViewById(R.id.genderInputLine);
        birthdateInputLine = findViewById(R.id.birthDateInputLine);
        ageInputLine = findViewById(R.id.ageInputLine);
        addressInputLine = findViewById(R.id.addressInputLine);
        numberInputLine = findViewById(R.id.numberInputLine);

        profile = findViewById(R.id.profile);
        avatar = findViewById(R.id.avatar);

        username.setEnabled(false);
        password.setEnabled(false);
        name.setEnabled(false);
        birthdate.setEnabled(false);
        gender.setEnabled(false);
        address.setEnabled(false);
        number.setEnabled(false);

        changeProfile = findViewById(R.id.changeProfile);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;
        params.setMargins(0, 0, 0, 30);
        avatar.setLayoutParams(params);


        changeProfile.setVisibility(View.GONE);

        currentUser = getIntent().getStringExtra("USER_ID");
        returnPress = getIntent().getStringExtra("RETURN");

        copyOfReturnPress = returnPress;

        progressDialog = new ProgressDialog(Account.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        progressDialog.setCancelable(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getProfile(currentUser);
            }
        }, 500);



        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar(){
                String Format = "MMMM dd, yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                birthdate.setText(sdf.format(calendar.getTime()));
            }
        };

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.clearFocus();
                password.clearFocus();
                address.clearFocus();
                number.clearFocus();
                new DatePickerDialog(Account.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        refreshPassword = findViewById(R.id.refreshPassword);

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        returnPress = returnPress + "4";

                        Intent intentHome = new Intent(Account.this, Patient.class);
                        intentHome.putExtra("USER_ID", currentUser);
                        intentHome.putExtra("RETURN", returnPress);
                        intentHome.putExtra("FIRST_TIME", "no");
                        startActivity(intentHome);
                        finish();
                        break;


                    case  R.id.contact:
                        returnPress = returnPress + "4";

                        Intent intentContact = new Intent(Account.this, Contact.class);
                        intentContact.putExtra("USER_ID", currentUser);
                        intentContact.putExtra("RETURN", returnPress);
                        intentContact.putExtra("FIRST_TIME", "no");
                        startActivity(intentContact);
                        finish();
                        break;

                    case  R.id.about:
                        returnPress = returnPress + "4";

                        Intent intentAbout = new Intent(Account.this, About.class);
                        intentAbout.putExtra("USER_ID", currentUser);
                        intentAbout.putExtra("RETURN", returnPress);
                        intentAbout.putExtra("FIRST_TIME", "no");
                        startActivity(intentAbout);
                        finish();
                        break;

                    case  R.id.logout:
                        new AlertDialog.Builder(Account.this)
                                .setMessage("Are you sure you want to logout?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentLogout = new Intent(Account.this, Login.class);
                                        startActivity(intentLogout);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                        break;


                }
                return false;
            }
        });

//        refreshPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refreshPassword(currentUser);
//            }
//        });


        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        password.setTextColor(Color.parseColor("#0EB021"));
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                buttonEdit.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
//                refreshPassword.setVisibility(View.VISIBLE);

                password.setEnabled(true);
                password.setTextColor(Color.parseColor("#0EB021"));
//                passwordInputLine.setBackgroundColor(Color.parseColor("#0EB021"));


                name.setEnabled(true);
                name.setTextColor(Color.parseColor("#0EB021"));
//                nameInputLine.setBackgroundColor(Color.parseColor("#0EB021"));

                gender.setEnabled(true);
                gender.setTextColor(Color.parseColor("#0EB021"));
//                genderInputLine.setBackgroundColor(Color.parseColor("#0EB021"));

                birthdate.setEnabled(true);
                birthdate.setTextColor(Color.parseColor("#0EB021"));
//                birthdateInputLine.setBackgroundColor(Color.parseColor("#0EB021"));

                address.setEnabled(true);
                address.setTextColor(Color.parseColor("#0EB021"));
//                addressInputLine.setBackgroundColor(Color.parseColor("#0EB021"));

                number.setEnabled(true);
                number.setTextColor(Color.parseColor("#0EB021"));
//                numberInputLine .setBackgroundColor(Color.parseColor("#0EB021"));

                changeProfile.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.gravity = Gravity.CENTER;
                params.setMargins(0, 0, 0, 0);
                avatar.setLayoutParams(params);

                gender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.clearFocus();
                        password.clearFocus();
                        address.clearFocus();
                        number.clearFocus();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Account.this);
                        View inflater = getLayoutInflater().inflate(R.layout.gender_selection, null);
                        TextView male = inflater.findViewById(R.id.male);
                        TextView female = inflater.findViewById(R.id.female);
                        builder.setView(inflater);

                        LinearLayout genderInputLine = findViewById(R.id.genderInputLine);


                        android.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        male.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        genderInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                                        gender.setTextColor(Color.parseColor("#0EB021"));
                                        gender.setText("Male");
                                        alertDialog.dismiss();
                                    }
                                }, 200);
                            }
                        });

                        female.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        genderInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                                        gender.setTextColor(Color.parseColor("#0EB021"));
                                        gender.setText("Female");
                                        alertDialog.dismiss();
                                    }
                                }, 200);
                            }
                        });

                    }
                });

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordValue = password.getText().toString();
                String nameValue = name.getText().toString();
                String genderValue = gender.getText().toString();
                String birthdateValue = birthdate.getText().toString();
                String addressValue = address.getText().toString();
                String numberValue = number.getText().toString();

                if(passwordValue.equals("")){
                    LinearLayout passwordInputLine = findViewById(R.id.passwordInputLine);
                    password.requestFocus();
                    passwordInputLine.setBackgroundColor(Color.RED);
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            LinearLayout passwordInputLine = findViewById(R.id.passwordInputLine);
                            passwordInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            password.setTextColor(Color.parseColor("#0EB021"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(password.getText().toString().isEmpty()){
                                passwordInputLine.setBackgroundColor(Color.RED);
                            }
                        }
                    });
                    Toast.makeText(Account.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                    if(nameValue.equals("") || nameValue.equals(" ")){
                        LinearLayout nameInputLine = findViewById(R.id.nameInputLine);
                        nameInputLine.setBackgroundColor(Color.RED);
                        name.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                nameInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(name.getText().toString().isEmpty()){
                                    nameInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });
                    }

                    if(addressValue.equals("") || addressValue.equals(" ")){
                        LinearLayout addressInputLine = findViewById(R.id.addressInputLine);
                        addressInputLine.setBackgroundColor(Color.RED);
                        address.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(address.getText().toString().isEmpty()){
                                    addressInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });
                    }

                    if(numberValue.equals("") || numberValue.equals(" ")){
                        LinearLayout numberInputLine = findViewById(R.id.numberInputLine);
                        numberInputLine.setBackgroundColor(Color.RED);
                        number.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(number.getText().toString().isEmpty()){
                                    numberInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });
                    }
                }

                else  if(nameValue.equals("") || nameValue.equals(" ")){
                    LinearLayout nameInputLine = findViewById(R.id.nameInputLine);
                    name.requestFocus();
                    nameInputLine.setBackgroundColor(Color.RED);
                    name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            LinearLayout nameInputLine = findViewById(R.id.nameInputLine);
                            nameInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            name.setTextColor(Color.parseColor("#0EB021"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(name.getText().toString().isEmpty()){
                                nameInputLine.setBackgroundColor(Color.RED);
                            }
                        }
                    });
                    Toast.makeText(Account.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();


                    if(addressValue.equals("") || addressValue.equals(" ")){
                        LinearLayout addressInputLine = findViewById(R.id.addressInputLine);
                        addressInputLine.setBackgroundColor(Color.RED);
                        address.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(address.getText().toString().isEmpty()){
                                    addressInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });
                    }

                    if(numberValue.equals("") || numberValue.equals(" ")){
                        LinearLayout numberInputLine = findViewById(R.id.numberInputLine);
                        numberInputLine.setBackgroundColor(Color.RED);
                        number.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(number.getText().toString().isEmpty()){
                                    numberInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });
                    }
                }

                else if(addressValue.equals("") || addressValue.equals(" ")){
                    LinearLayout addressInputLine = findViewById(R.id.addressInputLine);
                    address.requestFocus();
                    addressInputLine.setBackgroundColor(Color.RED);
                    address.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            LinearLayout addressInputLine = findViewById(R.id.addressInputLine);
                            addressInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            address.setTextColor(Color.parseColor("#0EB021"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(address.getText().toString().isEmpty()){
                                addressInputLine.setBackgroundColor(Color.RED);
                            }
                        }
                    });
                    Toast.makeText(Account.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                    if(numberValue.equals("") || numberValue.equals(" ")){
                        LinearLayout numberInputLine = findViewById(R.id.numberInputLine);
                        numberInputLine.setBackgroundColor(Color.RED);
                        number.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(number.getText().toString().isEmpty()){
                                    numberInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });
                    }
                }

                else if(numberValue.equals("") || numberValue.equals(" ")){
                    LinearLayout numberInputLine = findViewById(R.id.numberInputLine);
                    number.requestFocus();
                    numberInputLine.setBackgroundColor(Color.RED);
                    number.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            LinearLayout numberInputLine = findViewById(R.id.numberInputLine);
                            numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            number.setTextColor(Color.parseColor("#0EB021"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(number.getText().toString().isEmpty()){
                                numberInputLine.setBackgroundColor(Color.RED);
                            }
                        }
                    });
                    Toast.makeText(Account.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();
                }

                else if(passwordValue.length() < 8){
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            LinearLayout passwordInputLine = findViewById(R.id.passwordInputLine);
                            passwordInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            password.setTextColor(Color.parseColor("#0EB021"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(password.getText().toString().isEmpty()){
                                passwordInputLine.setBackgroundColor(Color.RED);
                            }
                        }
                    });

                    LinearLayout passwordInputLine = findViewById(R.id.passwordInputLine);
                    password.requestFocus();
                    passwordInputLine.setBackgroundColor(Color.RED);
                    password.setTextColor(Color.RED);
                    Toast.makeText(Account.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();

                }

                else if(passwordValue.matches("[0-9]+") || passwordValue.matches("[a-zA-Z]+")){
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            LinearLayout passwordInputLine = findViewById(R.id.passwordInputLine);
                            passwordInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            password.setTextColor(Color.parseColor("#0EB021"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(password.getText().toString().isEmpty()){
                                passwordInputLine.setBackgroundColor(Color.RED);
                            }
                        }
                    });
                    LinearLayout passwordInputLine = findViewById(R.id.passwordInputLine);
                    password.requestFocus();
                    passwordInputLine.setBackgroundColor(Color.RED);
                    password.setTextColor(Color.RED);
                    Toast.makeText(Account.this, "Password must be a combination of letters and numbers", Toast.LENGTH_SHORT).show();
                }

                else{
                    if(change.equals("1")){
                        new AlertDialog.Builder(Account.this)
                                .setMessage("Are you sure you want to update this information?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        name.clearFocus();
                                        password.clearFocus();
                                        address.clearFocus();
                                        number.clearFocus();

                                        progressDialog5 = new ProgressDialog(Account.this);
                                        progressDialog5.show();
                                        progressDialog5.setContentView(R.layout.progress_bar);
                                        progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                        progressDialog5.setCancelable(false);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                String url = "http://"+ipAddress+"/SmartCare/setProfile.php";
                                                String imageData;

                                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        String passwordValue = password.getText().toString();
                                                        String nameValue = name.getText().toString();
                                                        String birthdateValue = birthdate.getText().toString();
                                                        String genderValue = gender.getText().toString();
                                                        String addressValue = address.getText().toString();
                                                        String numberValue = number.getText().toString();

                                                        updateInformation(currentUser, passwordValue, nameValue, birthdateValue, genderValue, addressValue, numberValue, response);
                                                    }
                                                }, new Response.ErrorListener(){
                                                    @Override
                                                    public void onErrorResponse(VolleyError error){
                                                        progressDialog5.dismiss();

                                                        progressDialog6 = new ProgressDialog(Account.this);
                                                        progressDialog6.show();
                                                        progressDialog6.setContentView(R.layout.error_update);
                                                        progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                progressDialog6.dismiss();
                                                                cancelButton.performClick();
                                                            }
                                                        }, 2000);
                                                    }
                                                }){
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<>();
                                                        String imageData = imageToString(bitmap);
                                                        params.put("image", imageData);
                                                        params.put("currentUserID", currentUser);

                                                        return params;
                                                    }
                                                };
                                                RequestQueue requestQueue = Volley.newRequestQueue(Account.this);
                                                requestQueue.add(stringRequest);
                                            }
                                        }, 2000);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }

                    else{
                        new AlertDialog.Builder(Account.this)
                                .setMessage("Are you sure you want to update this information?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        name.clearFocus();
                                        password.clearFocus();
                                        address.clearFocus();
                                        number.clearFocus();

                                        progressDialog5 = new ProgressDialog(Account.this);
                                        progressDialog5.show();
                                        progressDialog5.setContentView(R.layout.progress_bar);
                                        progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                        progressDialog5.setCancelable(false);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                String profile="";
                                                String passwordValue = password.getText().toString();
                                                String nameValue = name.getText().toString();
                                                String birthdateValue = birthdate.getText().toString();
                                                String genderValue = gender.getText().toString();
                                                String addressValue = address.getText().toString();
                                                String numberValue = number.getText().toString();

                                                updateInformation(currentUser, passwordValue, nameValue, birthdateValue, genderValue, addressValue, numberValue, profile);
                                            }
                                        }, 2000);

                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdate.setFocusableInTouchMode(false);
                birthdate.setFocusable(false);

                password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        password.setTextColor(Color.parseColor("#757575"));
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                buttonEdit.setVisibility(View.VISIBLE);
//                refreshPassword.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);


                changeProfile.setVisibility(View.GONE);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.gravity = Gravity.CENTER;
                params.setMargins(0, 0, 0, 30);
                avatar.setLayoutParams(params);

                password.setText(passwordResponse);
                name.setText(staffNameResponse);
                gender.setText(genderResponse);
                birthdate.setText(birthdateResponse);
                address.setText(addressResponse);
                number.setText(numberResponse);

                password.setEnabled(false);
                password.setTextColor(Color.parseColor("#6B6C6B"));

                name.setEnabled(false);
                name.setTextColor(Color.parseColor("#6B6C6B"));

                birthdate.setEnabled(false);
                birthdate.setTextColor(Color.parseColor("#6B6C6B"));

                gender.setEnabled(false);
                gender.setTextColor(Color.parseColor("#6B6C6B"));

                address.setEnabled(false);
                address.setTextColor(Color.parseColor("#6B6C6B"));

                number.setEnabled(false);
                number.setTextColor(Color.parseColor("#6B6C6B"));
            }
        });

        changeProfile =findViewById(R.id.changeProfile);


        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change = "1";
                ActivityCompat.requestPermissions(Account.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(returnPress.length() > 1){
            // para ma concatinate ang return press para ang pina ka dulo lang kwaon
            copyOfReturnPress = returnPress;
            concatinatedReturnPress = returnPress.substring(returnPress.length() - 1);
            returnPress = concatinatedReturnPress;
        }

        if(returnPress.equals("0")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Account.this, Home.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("1")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Account.this, Patient.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("3")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Account.this, About.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("5")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Account.this, Contact.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("6")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Account.this, DismissPatient.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("7")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentDismiss = new Intent(Account.this, Monitoring.class);
            intentDismiss.putExtra("USER_ID", currentUser);
            intentDismiss.putExtra("RETURN", returnPress);
            intentDismiss.putExtra("FIRST_TIME", "no");
            startActivity(intentDismiss);
            finish();
        }

        else if(returnPress.equals("8")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentPatientLogs = new Intent(Account.this, PatientLogs.class);
            intentPatientLogs.putExtra("USER_ID", currentUser);
            intentPatientLogs.putExtra("RETURN", returnPress);
            intentPatientLogs.putExtra("FIRST_TIME", "no");
            startActivity(intentPatientLogs);
            finish();
        }

        else if(returnPress.equals("9")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentSharedPatient = new Intent(Account.this, SharePatient.class);
            intentSharedPatient.putExtra("USER_ID", currentUser);
            intentSharedPatient.putExtra("RETURN", returnPress);
            intentSharedPatient.putExtra("FIRST_TIME", "no");
            startActivity(intentSharedPatient);
            finish();
        }

        else if(returnPress.equals("a")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentSearchShare = new Intent(Account.this, SearchUserShare.class);
            intentSearchShare.putExtra("USER_ID", currentUser);
            intentSearchShare.putExtra("RETURN", returnPress);
            intentSearchShare.putExtra("FIRST_TIME", "no");
            startActivity(intentSearchShare);
            finish();
        }

        else if(returnPress.equals("b")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentRegister = new Intent(Account.this, Register.class);
            intentRegister.putExtra("USER_ID", currentUser);
            intentRegister.putExtra("RETURN", returnPress);
            intentRegister.putExtra("FIRST_TIME", "no");
            startActivity(intentRegister);
            finish();
        }

        else if(returnPress.equals("c")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Account.this, SharingMenu.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }

        else if(returnPress.equals("d")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Account.this, PatientInfo.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }

        else if(returnPress.equals("4")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Account.this, Account.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }

        else if(returnPress.equals("f")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Account.this, LiveMonitoring.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == CODE_GALLERY_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }

            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access gallery!" , Toast.LENGTH_LONG).show();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profile.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String path = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return path;
    }

    private void getProfile(String currentUserID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getProfile.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Account.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                        View header = navigationView.getHeaderView(0);//error
                        ImageView profile = header.findViewById(R.id.profile);
                        LinearLayout editAccount;
                        editAccount = header.findViewById(R.id.editAccount);

                        Glide.with(Account.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                        editAccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                returnPress = returnPress + "4";

                                Intent intentAccount = new Intent(Account.this, Account.class);
                                intentAccount.putExtra("USER_ID", currentUser);
                                intentAccount.putExtra("RETURN", returnPress);
                                intentAccount.putExtra("FIRST_TIME", "no");
                                startActivity(intentAccount);
                                finish();
                            }
                        });

                        getStaffName(currentUser);
                    }

                    else {
                        String profileString = response.substring(0,8);

                        if(profileString.equals("profile/")){
                            navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                            View header = navigationView.getHeaderView(0);//error
                            ImageView profile = header.findViewById(R.id.profile);
                            LinearLayout editAccount;
                            editAccount = header.findViewById(R.id.editAccount);

                            Glide.with(Account.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                            editAccount.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    returnPress = returnPress;

                                    Intent intentAccount = new Intent(Account.this, Account.class);
                                    intentAccount.putExtra("USER_ID", currentUser);
                                    intentAccount.putExtra("RETURN", returnPress);
                                    intentAccount.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAccount);
                                    finish();
                                }
                            });

                            getStaffName(currentUser);
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog4 = new ProgressDialog(Account.this);
                            progressDialog4.show();
                            progressDialog4.setContentView(R.layout.connectionfailure);
                            progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog4.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Account.this, Account.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }
                    }

                }

                //kung ang response is success
                else if(response.equals("failure")){

                    progressDialog.dismiss();

                    progressDialog1 = new ProgressDialog(Account.this);
                    progressDialog1.show();
                    progressDialog1.setContentView(R.layout.account_error_progress_bar1);
                    progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAccount = new Intent(Account.this, Account.class);
                            intentAccount.putExtra("USER_ID", currentUser);
                            intentAccount.putExtra("RETURN", returnPress);
                            intentAccount.putExtra("FIRST_TIME", "no");
                            startActivity(intentAccount);
                            finish();
                        }
                    }, 2000);
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String currentUserID = respObj.getString("currentUserID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                progressDialog2 = new ProgressDialog(Account.this);
                progressDialog2.show();
                progressDialog2.setContentView(R.layout.connectionfailure);
                progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog2.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Account.this, Account.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
                    }
                }, 2000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("currentUserID", currentUserID);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getStaffName(String currentUserID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getStaffName.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Account.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                        View header = navigationView.getHeaderView(0);
                        TextView staff_name = header.findViewById(R.id.staff_name);
                        staff_name.setText(response);
                        getStaffInfo(currentUser);
                    }

                    else{
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){

                            progressDialog.dismiss();

                            progressDialog4 = new ProgressDialog(Account.this);
                            progressDialog4.show();
                            progressDialog4.setContentView(R.layout.connectionfailure);
                            progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog4.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Account.this, Account.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }

                        else {
                            progressDialog.dismiss();

                            navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                            View header = navigationView.getHeaderView(0);
                            TextView staff_name = header.findViewById(R.id.staff_name);
                            staff_name.setText(response);
                            getStaffInfo(currentUser);
                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog1 = new ProgressDialog(Account.this);
                    progressDialog1.show();
                    progressDialog1.setContentView(R.layout.account_error_progress_bar1);
                    progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAccount = new Intent(Account.this, Account.class);
                            intentAccount.putExtra("USER_ID", currentUser);
                            intentAccount.putExtra("RETURN", returnPress);
                            intentAccount.putExtra("FIRST_TIME", "no");
                            startActivity(intentAccount);
                            finish();
                        }
                    }, 2000);
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String currentUserID = respObj.getString("currentUserID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                progressDialog2 = new ProgressDialog(Account.this);
                progressDialog2.show();
                progressDialog2.setContentView(R.layout.connectionfailure);
                progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog2.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Account.this, Account.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
                    }
                }, 2000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("currentUserID", currentUserID);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void updateInformation(String currentUserID, String passwordInput, String nameInput, String birthdateInput, String genderInput, String addressInput, String numberInput, String profileInput){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updateStaffInformation.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Account.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog5.dismiss();
                        progressDialog8 = new ProgressDialog(Account.this);
                        progressDialog8.show();
                        progressDialog8.setContentView(R.layout.updated_successful_progress_bar);
                        progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog8.dismiss();
                                returnPress = returnPress;

                                cancelButton.performClick();

                                Intent intentAccount = new Intent(Account.this, Account.class);
                                intentAccount.putExtra("USER_ID", currentUser);
                                intentAccount.putExtra("RETURN", returnPress);
                                intentAccount.putExtra("FIRST_TIME", "no");
                                startActivity(intentAccount);
                                finish();
                            }
                        }, 2000);
                    }

                    else{
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog5.dismiss();
                            progressDialog2 = new ProgressDialog(Account.this);
                            progressDialog2.show();
                            progressDialog2.setContentView(R.layout.connectionfailure);
                            progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog2.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Account.this, Account.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }

                        else {
                            progressDialog5.dismiss();
                            progressDialog8 = new ProgressDialog(Account.this);
                            progressDialog8.show();
                            progressDialog8.setContentView(R.layout.updated_successful_progress_bar);
                            progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog8.dismiss();
                                    returnPress = returnPress;

                                    cancelButton.performClick();

                                    Intent intentAccount = new Intent(Account.this, Account.class);
                                    intentAccount.putExtra("USER_ID", currentUser);
                                    intentAccount.putExtra("RETURN", returnPress);
                                    intentAccount.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAccount);
                                    finish();
                                }
                            }, 2000);
                        }
                    }

                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog5.dismiss();
                    progressDialog9 = new ProgressDialog(Account.this);
                    progressDialog9.show();
                    progressDialog9.setContentView(R.layout.failed_update_info);
                    progressDialog9.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAccount = new Intent(Account.this, Account.class);
                            intentAccount.putExtra("USER_ID", currentUser);
                            intentAccount.putExtra("RETURN", returnPress);
                            intentAccount.putExtra("FIRST_TIME", "no");
                            startActivity(intentAccount);
                            finish();
                        }
                    }, 2000);
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String currentUserID = respObj.getString("currentUserID");

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog5.dismiss();
                progressDialog2 = new ProgressDialog(Account.this);
                progressDialog2.show();
                progressDialog2.setContentView(R.layout.connectionfailure);
                progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog2.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Account.this, Account.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
                    }
                }, 2000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("currentUserID", currentUserID);
                params.put("passwordInput", passwordInput);
                params.put("nameInput", nameInput);
                params.put("birthdateInput", birthdateInput);
                params.put("genderInput", genderInput);
                params.put("addressInput", addressInput);
                params.put("numberInput", numberInput);
                params.put("profileInput", profileInput);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getStaffInfo(String currentUserID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getStaffInfo.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Account.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog.dismiss();

                        addStaffInfo(response);
                    }

                    else{
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog2 = new ProgressDialog(Account.this);
                            progressDialog2.show();
                            progressDialog2.setContentView(R.layout.connectionfailure);
                            progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog2.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Account.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Account.this, Account.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }

                        else {
                            progressDialog.dismiss();

                            LinearLayout middleLayout = findViewById(R.id.middleLayout);
                            middleLayout.setVisibility(View.VISIBLE);
                            addStaffInfo(response);

                        }
                    }


                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();
                    progressDialog3 = new ProgressDialog(Account.this);
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.account_error_progress_bar2);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAccount = new Intent(Account.this, Account.class);
                            intentAccount.putExtra("USER_ID", currentUser);
                            intentAccount.putExtra("RETURN", returnPress);
                            intentAccount.putExtra("FIRST_TIME", "no");
                            startActivity(intentAccount);
                            finish();
                        }
                    }, 2000);
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String currentUserID = respObj.getString("currentUserID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();
                progressDialog2 = new ProgressDialog(Account.this);
                progressDialog2.show();
                progressDialog2.setContentView(R.layout.connectionfailure);
                progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog2.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Account.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Account.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Account.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Account.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Account.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Account.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Account.this, Account.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
                    }
                }, 2000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("currentUserID", currentUserID);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }


    public void addStaffInfo(String responseResult) {

        int i = 0;
        TextView newLayoutClick;
        TextView numberOfRegisteredPatient;

        for (;;) {

            if (responseResult.equals(" ") || responseResult.equals("")) {
                break;
            }

            else {
                usernameResponse = responseResult.replaceAll("\\`.*", "");

                String newWord = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord.trim();

                responseResult = newWord;

                passwordResponse = responseResult.replaceAll("\\`.*", "");

                String newWord1 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord1.trim();

                responseResult = newWord1;

                staffNameResponse = responseResult.replaceAll("\\`.*", "");

                String newWord2 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord2.trim();

                responseResult = newWord2;

                birthdateResponse = responseResult.replaceAll("\\`.*", "");

                String newWord3 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord3.trim();

                responseResult = newWord3;

                genderResponse = responseResult.replaceAll("\\`.*", "");

                String newWord5 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord5.trim();

                responseResult = newWord5;

                addressResponse = responseResult.replaceAll("\\`.*", "");

                String newWord6 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord6.trim();

                responseResult = newWord6;

                numberResponse = responseResult.replaceAll("\\`.*", "");

                String newWord7 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord7.trim();

                responseResult = newWord7;

                profileResponse = responseResult.replaceAll("\\%.*", "");

                String newWord8 = responseResult.substring(responseResult.indexOf("%") + 1);
                newWord8.trim();

                responseResult = newWord8;

                username.setText(usernameResponse);
                password.setText(passwordResponse);
                name.setText(staffNameResponse);
                birthdate.setText(birthdateResponse);
                gender.setText(genderResponse);
                address.setText(addressResponse);
                number.setText(numberResponse);

                Glide.with(this).load("http://"+ipAddress+"/SmartCare/"+profileResponse).into(profile);

            }
        }
    }

    public void setNewStaffInformation(String responseResult) {
        String usernameResponse;
        String passwordResponse;
        String staffNameResponse;
        String birthdateResponse;
        String genderResponse;
        String addressResponse;
        String numberResponse;
        String newProfileResponse;

        for (;;) {

            if (responseResult.equals(" ") || responseResult.equals("")) {
                break;
            }

            else {
                usernameResponse = responseResult.replaceAll("\\`.*", "");

                String newWord = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord.trim();

                responseResult = newWord;

                passwordResponse = responseResult.replaceAll("\\`.*", "");

                String newWord1 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord1.trim();

                responseResult = newWord1;

                staffNameResponse = responseResult.replaceAll("\\`.*", "");

                String newWord2 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord2.trim();

                responseResult = newWord2;

                birthdateResponse = responseResult.replaceAll("\\`.*", "");

                String newWord3 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord3.trim();

                responseResult = newWord3;

                genderResponse = responseResult.replaceAll("\\`.*", "");

                String newWord5 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord5.trim();

                responseResult = newWord5;

                addressResponse = responseResult.replaceAll("\\`.*", "");

                String newWord6 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord6.trim();

                responseResult = newWord6;

                numberResponse = responseResult.replaceAll("\\`.*", "");

                String newWord7 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord7.trim();

                responseResult = newWord7;

                newProfileResponse = responseResult.replaceAll("\\%.*", "");

                String newWord8 = responseResult.substring(responseResult.indexOf("%") + 1);
                newWord8.trim();

                responseResult = newWord8;

                Glide.with(this).load("http://"+ipAddress+"/SmartCare/"+newProfileResponse).into(profile);

                username.setText(usernameResponse);
                password.setText(passwordResponse);
                name.setText(staffNameResponse);
                birthdate.setText(birthdateResponse);
                gender.setText(genderResponse);
                address.setText(addressResponse);
                number.setText(numberResponse);
            }
        }


    }


    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}