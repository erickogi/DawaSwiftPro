package com.dev.dawaswift.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.data.Constants
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.models.chat.Message
import com.dev.dawaswift.models.pharmacy.Pharmacy
import com.dev.dawaswift.ui.chat.ui.chat.ChatFragment
import com.dev.dawaswift.ui.chat.ui.chat.ChatThreadFragment

class ChatActivity : AppCompatActivity() {
    var message: Message? = null
    var pharmacy: Pharmacy? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        ViewUtils().makeFullScreen(this)


        try {
            pharmacy = intent.getSerializableExtra(Constants().PHARMACY) as Pharmacy
        } catch (x: Exception) {
            x.printStackTrace()
        }

        try {
            message = intent.getSerializableExtra(Constants().MESSAGE) as Message
        } catch (x: Exception) {
            x.printStackTrace()
        }






        if (pharmacy != null) {

            val fragment = ChatThreadFragment()

            val bundle = Bundle()
            bundle.apply { putSerializable(Constants().PHARMACY, pharmacy) }
            if (message != null) {
                bundle.apply { putSerializable(Constants().MESSAGE, message) }
            }


            fragment.arguments = bundle


            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()


        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, ChatFragment.newInstance())
                    .commitNow()
            }

        }
    }

}
