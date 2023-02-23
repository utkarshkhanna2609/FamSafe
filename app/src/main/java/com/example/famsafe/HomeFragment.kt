package com.example.famsafe

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    lateinit var inviteAdapter:InviteAdapter
    private val listOfContacts:ArrayList<ContactModel> =ArrayList()
    private lateinit var rootView:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_home, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listMembers= listOf<MemberModel>(
            MemberModel(
                "Utkarsh",
                "9th buildind, 2nd floor, maldiv road, manali 9th buildind, 2nd floor",
                "90%",
                "220"
            ),
            MemberModel(
                "Sachin",
                "10th buildind, 3rd floor, maldiv road, manali 10th buildind, 3rd floor",
                "80%",
                "180"
            ),
            MemberModel(
                "Shikha",
                "11th buildind, 4th floor, maldiv road, manali 11th buildind, 4th floor",
                "70%",
                "200"
            ),
            MemberModel(
                "Caezar",
                "12th buildind, 5th floor, maldiv road, manali 12th buildind, 5th floor",
                "60%",
                "190"
            )
        )

        val adapter=memberAdapter(listMembers)
        val recycler=requireView().findViewById<RecyclerView>(R.id.recycler_member)
        recycler.layoutManager=LinearLayoutManager(requireContext())
        recycler.adapter=adapter

        inviteAdapter=InviteAdapter(listOfContacts)
        fetchDatabaseContacts()

        CoroutineScope(Dispatchers.IO).launch {
            insertAllContacts(fetchContacts())

        }


        val inviteRecycler=requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        inviteRecycler.adapter=inviteAdapter
    }

    private fun fetchDatabaseContacts(){
        val database=ContactsDB.DataBase(requireContext())
        database.contactsDao().getAllContacts().observe(viewLifecycleOwner){
            listOfContacts.clear()
            listOfContacts.addAll(it)
            inviteAdapter.notifyDataSetChanged()
        }

    }

    private suspend fun insertAllContacts(listOfContacts: ArrayList<ContactModel>) {
        val database=ContactsDB.DataBase(requireContext())
        database.contactsDao().insertAll(listOfContacts)
    }



    private fun fetchContacts(): ArrayList<ContactModel> {
        val listContacts: ArrayList<ContactModel> = ArrayList()
        val cr = requireActivity().contentResolver
        val cursor = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val nameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val hasPhoneColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)

                if (hasPhoneColumnIndex > 0) {
                    val id = cursor.getString(idColumnIndex)
                    val name = cursor.getString(nameColumnIndex)

                    val phoneCursor = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )

                    if (phoneCursor != null && phoneCursor.count > 0) {
                        while (phoneCursor.moveToNext()) {
                            val phoneColumnIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            val phoneNum = phoneCursor.getString(phoneColumnIndex)
                            listContacts.add(ContactModel(name, phoneNum))
                        }
                    }

                    phoneCursor?.close()
                }
            }
        }

        cursor?.close()

        return listContacts
    }



    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }


}
/// Please ask the price of 14 pro and 14 pro max 128 gb, my friend needs
