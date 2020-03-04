package com.example.exam_instagram

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn_Login.setOnClickListener() {
            signinAndSignup()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        btn_Gmail_Signin.setOnClickListener() {
            //1번째 구글 로그인 단계
            googleLogin()
        }


    }

    fun googleLogin() {
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GOOGLE_LOGIN_CODE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                var account = result.signInAccount
                //2번째 단계 (구글 로그인 - > 파이어베이스)
                firebaseAtuhWithGoole(account)
            }else{
                Toast.makeText(this,requestCode.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAtuhWithGoole(account: GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //로그인
                    moveMainPage(task.result?.user)
                } else {
                    //에러메세지
                    Toast.makeText(this, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                }

            }
    }

    //회원가입
    fun signinAndSignup() {
        auth?.createUserWithEmailAndPassword(edt_Email.text.toString(), edt_Pwd.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //인증 성공시 유저 어카운트 만듬
                    moveMainPage(task.result?.user)
                } else {
                    signinEmail()
                }

            }
    }


    fun signinEmail() {
        auth?.createUserWithEmailAndPassword(edt_Email.text.toString(), edt_Pwd.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //로그인
                    moveMainPage(task.result?.user)
                } else {
                    //에러메세지
                    Toast.makeText(this, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                }

            }
    }

    fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}
