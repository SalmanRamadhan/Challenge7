package com.example.challenge7.authentication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.challenge7.api.NetworkHelper
import com.example.challenge7.databinding.FragmentLoginBinding
import com.example.challenge7.helper.NetworkConnection
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.MenuActivity
import com.example.challenge7.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {


    var binding: FragmentLoginBinding? = null
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
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding?.btnLogin?.setOnClickListener {
            login()
        }

        binding?.tvSignUp?.setOnClickListener {
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.add(((view as ViewGroup).parent as View).id, SignUpFragment())
            fragmentTransaction?.commit()
        }

        return binding?.root
    }

    private fun login() {

        val email = binding?.edtEmail?.text.toString()
        val pass = binding?.edtPassword?.text.toString()

        with(progressDialog) {

            setMessage("Loading...")
            setCancelable(false)
            show()
        }

        validation(email, pass)

        NetworkHelper.instance.login(email, pass).enqueue(object : Callback<GetUserResponse> {
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                println("pesan -> on Respons")
                if (response.isSuccessful) {
                    val respon = response.body()
                    sharedPreferences.setStatusLogin(true)
                    sharedPreferences.setUser(respon?.data!!)

                    startActivity(Intent(activity, MenuActivity::class.java))
                    Toast.makeText(activity, "Berhasil Login", Toast.LENGTH_LONG).show()
                    activity?.finish()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(activity, "  Email atau Password Salah", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                println("pesan -> On Failure")
                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validation(email: String, pass: String) {

        if (email.isEmpty()) {
            progressDialog.dismiss()
            binding?.edtEmail?.error = "Isi dulu !"
            return
        }

        if (pass.isEmpty()) {
            progressDialog.dismiss()
            binding?.edtPassword?.error = "Isi dulu !"
            return
        }
    }
}