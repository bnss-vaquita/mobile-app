package bnss.mobile.a2famobile;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class UserFileTransferActivity extends AppCompatActivity {


    final int FILE_CHOOSER_REQUEST_CODE = 3;

    TextView textView_targetUser;
    TextView textView_targetFile;

    Uri targetFile = null;

    Button button_fileSelector;
    Button button_fileTransfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_file_transfer);

        Intent intent = getIntent();
        VaquitaUser user = (VaquitaUser) intent.getSerializableExtra("vaquitaUser");

        textView_targetUser = findViewById(R.id.textView_targetUser);
        textView_targetUser.setText(user.toString());

        textView_targetFile = findViewById(R.id.textView_targetFile);

        button_fileSelector = findViewById(R.id.button_fileSelector);
        button_fileSelector.setOnClickListener(openFilePicker());

        button_fileTransfer = findViewById(R.id.button_fileTransfer);
        button_fileTransfer.setEnabled(false);
        button_fileTransfer.setOnClickListener(transferFile());

    }


    private View.OnClickListener openFilePicker() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent fileAccessIntent = new Intent(Intent.ACTION_GET_CONTENT);

                fileAccessIntent.addCategory(Intent.CATEGORY_OPENABLE);
                fileAccessIntent.setType("*/*");

                Intent fileChooser = Intent.createChooser(fileAccessIntent,"fileChooserIntent");
                startActivityForResult(fileChooser,FILE_CHOOSER_REQUEST_CODE );
                fileChooser.getData();
            }
        };
    }

    private View.OnClickListener transferFile() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case FILE_CHOOSER_REQUEST_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        targetFile = data.getData();
                        Log.e("userFileTransfer", targetFile.toString());
                        textView_targetFile.setText(targetFile.getLastPathSegment());
                        button_fileTransfer.setEnabled(true);
                        break;
                    default:
                        Log.e("UserFileTransfer", "File_chooser - resultCode: "+ resultCode);
                        Log.e("UserFileTransfer", "File_chooser - intent: "+ (data != null? data.toString(): "null"));


                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
