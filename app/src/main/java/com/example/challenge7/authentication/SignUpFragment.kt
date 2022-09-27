package com.example.challenge7.authentication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.challenge7.api.NetworkHelper
import com.example.challenge7.databinding.FragmentSignUpBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.MenuActivity
import com.example.challenge7.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    var binding: FragmentSignUpBinding? = null
    private val sharedPreferences by lazy {
        SharedPreferences(requireActivity())
    }
    val progressDialog: ProgressDialog by lazy {
        ProgressDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        binding?.btnSignUp?.setOnClickListener {
            register()
        }

        binding?.tvLogin?.setOnClickListener {
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.add(((view as ViewGroup).parent as View).id,LoginFragment())
            fragmentTransaction?.commit()
        }

        return binding?.root
    }

    private fun register() {
        val email = binding?.edtEmail?.text.toString()
        val username = binding?.edtUserName?.text.toString()
        val pass = binding?.edtPassword?.text.toString()
        val rePass = binding?.edtRePassword?.text.toString()

        with(progressDialog) {

            setMessage("Loading...")
            setCancelable(false)
            show()
        }

        validation(email,username,pass,rePass)


        NetworkHelper.instance.register(email,username,pass).enqueue(object : Callback<GetUserResponse> {
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {

                val respon = response.body()
                if (response.isSuccessful) {
                    if (respon?.success == true) {
                            sharedPreferences.setStatusLogin(true)
                            //mengarahkan ke fragment login
                            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                            fragmentTransaction?.add(((view as ViewGroup).parent as View).id,LoginFragment())
                            fragmentTransaction?.commit()
                            Toast.makeText(activity, "Berhasil Register", Toast.LENGTH_LONG).show()
                    } else{
//                        progressDialog.dismiss()
                        Toast.makeText(activity, "${respon?.errors}", Toast.LENGTH_LONG).show()
                    }
                }else{
//                    progressDialog.dismiss()
                    Toast.makeText(activity, "${respon?.errors}", Toast.LENGTH_LONG).show()
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validation(email: String, username: String, pass: String, rePass: String) {

        when {
            email.isEmpty() -> {
                progressDialog.dismiss()
                binding?.edtEmail?.error = "Isi dulu!"
                return
            }
            //email harus mengandung @
            !email.contains("@") -> {
                progressDialog.dismiss()
                binding?.edtEmail?.error = "Email harus mengandung @"
                return
            }
            //email tidak boleh ada spasi
            email.contains(" ") -> {
                progressDialog.dismiss()
                binding?.edtEmail?.error = "Email tidak boleh mengandung spasi"
                return
            }
            //format email harus sesuai dengan email
            !email.contains(".") -> {
                progressDialog.dismiss()
                binding?.edtEmail?.error = "Email tidak valid"
                return
            }

            username.isEmpty() -> {
                progressDialog.dismiss()
                binding?.edtUserName?.error = "Isi dulu!"
                return
            }
            //username tidak boleh ada spasi
            username.contains(" ") -> {
                progressDialog.dismiss()
                binding?.edtUserName?.error = "Username tidak boleh mengandung spasi"
                return
            }
            //username min 8 karakter
            username.length < 8 -> {
                progressDialog.dismiss()
                binding?.edtUserName?.error = "Username minimal 8 karakter"
                return
            }
            //username hanya boleh huruf a sampai z
            !username.matches("^[a-zA-Z]*$".toRegex()) -> {
                progressDialog.dismiss()
                binding?.edtUserName?.error = "Username hanya boleh huruf a sampai z"
                return
            }

            pass.isEmpty() -> {
                progressDialog.dismiss()
                binding?.edtPassword?.error = "Isi dulu!"
                return
            }
            //password minimal 8 karakter
            pass.length < 8 -> {
                progressDialog.dismiss()
                binding?.edtPassword?.error = "Password minimal 8 karakter"
                return
            }
            //password tidak boleh ada spasi
            pass.contains(" ") -> {
                progressDialog.dismiss()
                binding?.edtPassword?.error = "Password tidak boleh mengandung spasi"
                return
            }
            //password harus mengandung minimal 1 huruf besar
            !pass.matches(".*[A-Z].*".toRegex()) -> {
                progressDialog.dismiss()
                binding?.edtPassword?.error = "Password harus mengandung minimal 1 huruf besar"
                return
            }
            //password harus mengandung minimal 1 angka
            !pass.matches(".*[0-9].*".toRegex()) -> {
                progressDialog.dismiss()
                binding?.edtPassword?.error = "Password harus mengandung minimal 1 angka"
                return
            }
            //password harus mengandung minimal 1 karakter spesial
            !pass.matches(".*[!@#\$%^&*()_+].*".toRegex()) -> {
                progressDialog.dismiss()
                binding?.edtPassword?.error = "Password harus mengandung minimal 1 karakter spesial"
                return
            }

            rePass.isEmpty() -> {
                progressDialog.dismiss()
                binding?.edtRePassword?.error = "Isi dulu!"
                return
            }
            pass != rePass -> {
                progressDialog.dismiss()
                binding?.edtRePassword?.error = "Password tidak sama!"
                return
            }
        }

    }

}