package cn.dianyinhuoban.app2.mvp.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import cn.dianyinhuoban.app2.PWDTools
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.mvp.home.view.HomeActivity
import cn.dianyinhuoban.app2.mvp.login.contract.LoginContract
import cn.dianyinhuoban.app2.mvp.login.presenter.LoginPresenter
import com.wareroom.lib_base.ui.BaseActivity
import com.wareroom.lib_base.utils.ValidatorUtils
import com.wareroom.lib_base.utils.cache.MMKVUtil
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseActivity<LoginPresenter?>(), LoginContract.View {
    private val edPhone: EditText by lazy {
        findViewById(R.id.ed_phone)
    }
    private val edPassword: EditText by lazy {
        findViewById(R.id.ed_password)
    }
    private val btnSubmit: Button by lazy {
        findViewById(R.id.btn_submit)
    }

    private var showBackBtn = false
    override fun getPresenter(): LoginPresenter? {
        return LoginPresenter(this)
    }

    override fun toolbarIsEnable(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        val defPhone = MMKVUtil.getUserName()
        val defPassword = MMKVUtil.getLoginPassword()
        edPhone.setText(defPhone)
        edPassword.setText(defPassword)
        edPhone.setSelection(defPhone.length)
        edPassword.setSelection(defPassword.length)
    }

    override fun handleIntent(bundle: Bundle?) {
        super.handleIntent(bundle)
        bundle?.let {
            showBackBtn = it.getBoolean("showBackBtn", false)
        }
    }

    private fun initView() {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                setSubmitButtonEnable()
            }
        }

        edPhone.addTextChangedListener(textWatcher)
        edPassword.addTextChangedListener(textWatcher)
        findViewById<ImageView>(R.id.iv_eye).setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                //????????????
                edPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                //??????????????????
                edPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            val password = edPassword.text.toString()
            edPassword.setSelection(password.length)
        }

        //??????
        findViewById<TextView>(R.id.tv_register).setOnClickListener {
//            startActivity(Intent(LoginActivity@ this, RegisterActivity::class.java))
        }
        //????????????
        findViewById<TextView>(R.id.tv_forget).setOnClickListener {
//            startActivity(Intent(LoginActivity@ this, ResetPasswordActivity::class.java))
        }
        //??????
        btnSubmit.setOnClickListener {
            submitLogin()
        }
    }

    /**
     * ?????????????????????????????????
     */
    private fun setSubmitButtonEnable() {
        val phone = edPhone.text.toString()
        val password = edPassword.text.toString()
        btnSubmit.isEnabled =
            (ValidatorUtils.isPassword(password) && ValidatorUtils.isMobile(phone))
    }

    /**
     * ??????
     */
    private fun submitLogin() {
        val userName = edPhone.text.toString()
        val password = edPassword.text.toString()
        mPresenter?.submitLogin(userName, password)
    }

    override fun onLoginSuccess() {
        startActivity(Intent(LoginActivity@ this, HomeActivity::class.java))
        finish()
    }

    override fun onLoginError() {

    }
}