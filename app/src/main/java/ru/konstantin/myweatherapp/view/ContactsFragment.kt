package ru.konstantin.myweatherapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import ru.konstantin.myweatherapp.R
import ru.konstantin.myweatherapp.databinding.FragmentContactsBinding
import ru.konstantin.myweatherapp.model.AppStateContactList
import ru.konstantin.myweatherapp.viewmodel.ContactsViewModel

class ContactsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactsFragment()
    }

    private var _binding: FragmentContactsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ContactsViewModel by lazy {
        ViewModelProvider(this).get(ContactsViewModel::class.java)
    }

    val adapter: ContactAdapter by lazy {
        ContactAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(layoutInflater, container, false)
        binding.contactListList.adapter = adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.contacts.observe(viewLifecycleOwner) {
            renderData(it)
        }
        checkPermission()
    }

    private fun renderData(state: AppStateContactList) {
        when (state) {
            is AppStateContactList.Success -> {
                binding.contactListList.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                adapter.contacts = state.contactList
            }
            is AppStateContactList.Loading -> {
                binding.contactListList.hide()
                binding.includedLoadingLayout.loadingLayout.show()
            }
        }
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS)
                        ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle(R.string.warning_title)
                        .setMessage(R.string.couse)
                        .setPositiveButton(R.string.agree_text) { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                        .setNegativeButton(R.string.disagree_text) { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                getContacts()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Объяснение")
                        .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        }


    private fun getContacts() {
        viewModel.getContacts()
        Toast.makeText(requireContext(), "Read Contact List", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun View.show(): View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    private fun View.hide(): View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }
}