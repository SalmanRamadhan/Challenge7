package com.example.challenge7.authentication

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

        if (email.isEmpty()) {
            binding?.edtEmail?.error = "Isi dulu!"
            return
        }

        if (username.isEmpty()) {
            binding?.edtUserName?.error = "Isi dulu!"
            return
        }

        if (pass.isEmpty()) {
            binding?.edtPassword?.error = "Isi dulu!"
            return
        }
        if (rePass.isEmpty()) {
            binding?.edtRePassword?.error = "Isi dulu!"
            return
        }
        //cek ulang password
        if (pass != rePass) {
            binding?.edtRePassword?.error = "Password tidak sama!"
            return
        }


        NetworkHelper.instance.register(email,pass, username).enqueue(object : Callback<GetUserResponse> {
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                val respon = response.body()

                if (response.isSuccessful) {
                    if (respon?.success == true) {
                        respon?.let {
                            sharedPreferences.setStatusLogin(true)
                            //mengarahkan ke fragment login
                            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                            fragmentTransaction?.add(((view as ViewGroup).parent as View).id,LoginFragment())
                            fragmentTransaction?.commit()
                            Toast.makeText(activity, "Berhasil Register", Toast.LENGTH_LONG).show()
                        }
                    } else{
                        Toast.makeText(activity, "Gagal Register", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

}