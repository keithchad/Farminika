package com.farminikagroup.farminika.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.adapter.MessageAdapter
import com.farminikagroup.farminika.data.model.Expert
import com.farminikagroup.farminika.data.model.Message
import com.farminikagroup.farminika.data.model.Users
import com.farminikagroup.farminika.data.utils.Constants
import com.farminikagroup.farminika.data.utils.PreferenceManager
import com.farminikagroup.farminika.data.viewmodel.InformationViewModel
import com.farminikagroup.farminika.data.viewmodel.UserViewModel
import com.farminikagroup.farminika.databinding.DeleteLayoutBinding
import com.farminikagroup.farminika.databinding.FragmentInformationBinding
import com.farminikagroup.farminika.databinding.FragmentMessageBinding
import com.farminikagroup.farminika.databinding.PaymentLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_expert.*
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*
import kotlin.collections.ArrayList

class MessageFragment : Fragment() {

    private lateinit var informationViewModel: InformationViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var jobTitle: String
    private lateinit var budget: String
    private lateinit var location: String
    private lateinit var description: String
    private lateinit var messageAdapter: MessageAdapter
    lateinit var list: ArrayList<Message>
    lateinit var receiverRoom: String
    lateinit var senderRoom: String
    lateinit var senderUid: String
    lateinit var receiverUid: String
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference

    lateinit var fragmentMessageBinding: FragmentMessageBinding
    val binding get() = fragmentMessageBinding

    private lateinit var preferenceManager: PreferenceManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMessageBinding = FragmentMessageBinding.inflate(inflater, container, false)

        informationViewModel = ViewModelProvider(requireActivity())[InformationViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        preferenceManager = PreferenceManager(requireActivity())

        informationViewModel.budget.observe(requireActivity()){ it ->
            budget = it
        }
        informationViewModel.jobTitle.observe(requireActivity()){ it ->
            jobTitle = it
        }
        informationViewModel.location.observe(requireActivity()){
            location = it
        }
        informationViewModel.description.observe(requireActivity()){
            description = it
        }

        list = ArrayList()
        database = FirebaseDatabase.getInstance()
        receiverUid = preferenceManager.getString(Constants.KEY_PREFERENCE_USER_ID).toString()
        Toast.makeText(requireActivity(), receiverUid, Toast.LENGTH_SHORT).show()
        senderUid = FirebaseAuth.getInstance().uid.toString()
        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
        messageAdapter = MessageAdapter(requireActivity(), list, senderRoom, receiverRoom)
        binding.recyclerViewMesssage.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewMesssage.adapter = messageAdapter
        getUserData()

        database.reference.child("Chats").child("message")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    list.clear()
                    for (dataSnapshot in snapshot.children) {
                        val message: Message? = dataSnapshot.getValue(Message::class.java)
                        message?.messageId = dataSnapshot.key
                        list.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        binding.sendMessageButton.setOnClickListener {
            val textMessage = binding.editTextMessage.text.toString()
            val date = Date()
            val message = Message(textMessage, senderUid, date.time)

            binding.editTextMessage.setText("")
            val randomKey = database.reference.push().key
            val lastMessageObject = HashMap<String, Any>()
            lastMessageObject["lastMessage"] = message.message!!
            lastMessageObject["lastMessageTime"] = date.time

            database.reference.child("Chats").child(senderRoom)
                .updateChildren(lastMessageObject)
            database.reference.child("Chats").child(receiverRoom)
                .updateChildren(lastMessageObject)
            database.reference.child("Chats")
                .child("message")
                .child(randomKey!!)
                .setValue(message).addOnSuccessListener {
                    database.reference.child("Chats")
                        .child(receiverRoom)
                        .child("message")
                        .child(randomKey)
                        .setValue(message)
                        .addOnSuccessListener {
                            Toast.makeText(requireActivity(), "Sent", Toast.LENGTH_SHORT).show()
                        }
                }
        }
        binding.buttonAttachment.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 25)
        }
        binding.more.setOnClickListener {
            val view = LayoutInflater.from(requireActivity()).inflate(R.layout.payment_layout, null)
            val binding = PaymentLayoutBinding.bind(view)
            val dialog = AlertDialog.Builder(context).setTitle("Payment Method")
                .setView(binding.root)
                .create()
            binding.mpesaTransfer.setOnClickListener {
                findNavController().navigate(R.id.action_messageFragment_to_mpesaFragment)
            }
            binding.cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getUserData() {
        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser!!

        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(senderUid)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allUsers: Users? = snapshot.getValue(Users::class.java)
                if (allUsers != null) {
                    binding.textUserNameMessage.text = allUsers.userName
                    Glide.with(context!!).load(allUsers.imageProfile).into(binding.imageProfileMessage)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

}