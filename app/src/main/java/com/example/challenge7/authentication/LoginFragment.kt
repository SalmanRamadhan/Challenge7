package com.example.challenge7.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.challenge7.R
import com.example.challenge7.api.NetworkHelper
import com.example.challenge7.databinding.FragmentLoginBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.MenuActivity
import com.example.challenge7.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    companion object {
        var SUCCESS = 0
    }

    var binding: FragmentLoginBinding? = null
    lateinit var sph: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        sph = SharedPreferences(requireActivity())

        binding?.btnLogin?.setOnClickListener {
            login()
        }

        binding?.tvSignUp?.setOnClickListener {
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.add(((view as ViewGroup).parent as View).id,SignUpFragment())
            fragmentTransaction?.commit()
        }

        return binding?.root
    }

    private fun login() {

        val email = binding?.edtEmail?.text.toString()
        val pass = binding?.edtPassword?.text.toString()

        validation(email, pass)

        NetworkHelper.instance.login(email,pass).enqueue(object : Callback<GetUserResponse>{
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                val respon = response.body()

                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        sph.setStatusLogin(true)
                        sph.setUser(respon?.data!!)

                        startActivity(Intent(activity, MenuActivity::class.java))
                        Toast.makeText(activity, "Berhasil Login", Toast.LENGTH_LONG).show()
                        activity?.finish()
                    } else{
                        Toast.makeText(activity, "Gagal Login", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                if (t.message == "Wrong password!") {
                    Toast.makeText(activity, "Wrong password!", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun validation(email: String, pass: String) {

        if (email.isEmpty()) {
            binding?.edtEmail?.error = "Isi dulu !"
        }

        if (pass.isEmpty()) {
            binding?.edtPassword?.error = "Isi dulu !"
        }
    }
}