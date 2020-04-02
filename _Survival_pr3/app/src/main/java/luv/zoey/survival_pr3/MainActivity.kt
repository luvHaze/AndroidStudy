package luv.zoey.survival_pr3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {

            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()

        }
        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                webView.loadUrl(urlEditText.text.toString())
                true
            }
            else {
                false
            }

        }
        webView.loadUrl("http://www.google.com")
    }

    override fun onBackPressed() {

        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }


    }
}
