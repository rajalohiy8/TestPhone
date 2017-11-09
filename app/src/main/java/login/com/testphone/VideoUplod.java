package login.com.testphone;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class VideoUplod extends AppCompatActivity {
    private static final int RC_PDF_PICKER = 2;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPDFStorageReference;
    public static final int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_uplod);

       // VideoView videoView =(VideoView)findViewById(R.id.image);


        //Creating MediaController
       // MediaController mediaController= new MediaController(this);
       // mediaController.setAnchorView(videoView);


        //specify the location of media file
       // Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

        //Setting MediaController and URI, then starting the videoView
       // videoView.setMediaController(mediaController);
       // videoView.setVideoURI(uri);
        //videoView.requestFocus();
        //videoView.start();



        mFirebaseStorage =FirebaseStorage.getInstance();
        mChatPDFStorageReference = mFirebaseStorage.getReference().child("Video");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PDF_PICKER);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
            //for photo storage check
        } else if (requestCode == RC_PDF_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Get a reference to store file at chat_photos/<FILENAME>

            StorageReference photoRef = mChatPDFStorageReference.child(selectedImageUri.getLastPathSegment());

            // Upload file to Firebase Storage

            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            @SuppressWarnings("VisibleForTests")   Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            // Set the download URL to the message box, so that the user can send it to the database

                        }
                    });
        }
    }
}


