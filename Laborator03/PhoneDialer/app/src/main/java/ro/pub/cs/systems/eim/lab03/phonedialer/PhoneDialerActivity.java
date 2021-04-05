package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
//import ro.pub.cs.systems.eim.lab03.phonedialer.general.Constants;

import java.util.ArrayList;

import static android.provider.SyncStateContract.*;

public class PhoneDialerActivity extends AppCompatActivity {

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private static final int PERMISSION_REQUEST_CALL_PHONE = 0;

    //Vector butoane tipul Button
    public int[] buttonsId = {
            R.id.button_1,
            R.id.button_2,
            R.id.button_3,
            R.id.button_4,
            R.id.button_5,
            R.id.button_6,
            R.id.button_7,
            R.id.button_8,
            R.id.button_9,
            R.id.button_star,
            R.id.button_0,
            R.id.button_hashtag
    };
    //Vector butoane tipul ImageButton
    public int[] imageButtonsId = {
            R.id.imageButton_backspace,
            R.id.imageButton_call,
            R.id.imageButton_endcall
    };

    //TODO: Să se implementeze o clasă ascultător pentru tratarea evenimentelor de tip apăsare de buton
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText phoneNumberEditText = (EditText)findViewById(R.id.editTextPhone);
            String phoneNumber = phoneNumberEditText.getText().toString();

            //TODO: Pentru butoanele ce conțin cifre sau caracterele * / #, se va adăuga simbolul corespunzător la numărul de telefon care se dorește format
            if(view instanceof Button) {
                phoneNumberEditText.setText(phoneNumber+((Button)view).getText().toString());
            }

            if(view instanceof ImageButton) {
                //TODO: Pentru butonul de corecție, se va șterge ultimul caracter (în cazul în care numărul de telefon nu este vid);
                if(view.getId() == R.id.imageButton_backspace) {
                    if (phoneNumber.length() > 0) {
                        phoneNumberEditText.setText(phoneNumber.substring(0, phoneNumber.length() - 1));
                    }
                }
                //TODO: pentru butonul de apel, se va invoca intenția care realizează legătura telefonică;
                // întrucât se compilează proiectul Android folosind o versiune mai mare decât Marshmelow (6.0),
                // este necesar să fie solicitată permisiunea de efectuare a apelului telefonic la momentul rulării:
                if(view.getId() == R.id.imageButton_call) {
                    if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PhoneDialerActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSION_REQUEST_CALL_PHONE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                        startActivity(intent);
                    }
                }
                //TODO: pentru butonul de oprire, se va închide activitatea
                if(view.getId() == R.id.imageButton_endcall) {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        //TODO: să se blocheze tranziția între modurile portrait și landscape
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ArrayList<? super View> phoneButtons = new ArrayList<View>();
        for (int index = 0; index < buttonsId.length; index++) {
            Button textButton = (Button)findViewById(buttonsId[index]);
            textButton.setOnClickListener(buttonClickListener);
            phoneButtons.add(textButton);
        }
        for (int index = 0; index < imageButtonsId.length; index++) {
            ImageButton imageButton = (ImageButton)findViewById(imageButtonsId[index]);
            imageButton.setOnClickListener(buttonClickListener);
            phoneButtons.add(imageButton);
        }
    }
}