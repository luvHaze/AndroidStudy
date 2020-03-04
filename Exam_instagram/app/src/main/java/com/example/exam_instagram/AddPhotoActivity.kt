package com.example.exam_instagram

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exam_instagram.navigation.model.ContentDTO
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add_photo.*
import java.text.SimpleDateFormat
import java.util.*

class AddPhotoActivity : AppCompatActivity() {

    //Request code
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage: StorageReference? = null
    var photoUri: Uri? = null
    var auth: FirebaseAuth? = null
    var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        //Init Storage
        storage = FirebaseStorage.getInstance().getReference()
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        //Open the album
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)


        // add image upload event
        btn_upload.setOnClickListener() {
            contentUpload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                //이미지의 경로가 넘어오게 된다.
                photoUri = data?.data
                addphoto_image.setImageURI(photoUri)
            } else {
                // 취소버튼 눌렀을때 작동하는 부분
                finish()

            }
        }

    }

    fun contentUpload() {
        //make Filename

        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE " + timestamp + ".png"
        var storageRef = storage?.child("images")?.child(imageFileName)
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener{
            storageRef.downloadUrl.addOnSuccessListener {uri->
                var contentDTO = ContentDTO()

                //Inset downloadUrl of image
                contentDTO.imageUrl = uri.toString()

                //Inset uid of user
                contentDTO.uid = auth?.currentUser?.uid

                //Inset userId
                contentDTO.userID = auth?.currentUser?.email

                //Insert explain of content
                contentDTO.explain = addphoto_explain.text.toString()

                //Inset timestamp
                contentDTO.timestamp = System.currentTimeMillis()

                firestore?.collection("images")?.document()?.set(contentDTO)

                setResult(Activity.RESULT_OK)

                finish()
            }
            Toast.makeText(this, getString(R.string.upload_success), Toast.LENGTH_LONG).show()
        }

    }

     /* //File Upload (Callback)
      //promise method
        storageRef?.putFile(photoUri!!)?.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener() { uri ->

            Toast.makeText(this,"진입",Toast.LENGTH_LONG).show()

            val contentDTO = ContentDTO()

            //Inset downloadUrl of image
            contentDTO.imageUrl = uri.toString()

            //Inset uid of user
            contentDTO.uid = auth?.currentUser?.uid

            //Inset userId
            contentDTO.userID = auth?.currentUser?.email

            //Insert explain of content
            contentDTO.explain = addphoto_explain.text.toString()

            //Inset timestamp
            contentDTO.timestamp = System.currentTimeMillis()
            Toast.makeText(this,"진입2",Toast.LENGTH_LONG).show()
            firestore?.collection("images")?.document()?.set(contentDTO)

            setResult(Activity.RESULT_OK)
            finish()

        }
     */
}

